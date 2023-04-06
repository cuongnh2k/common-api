package space.cuongnh2k.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.utils.URIPathUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Log4j2
@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {
    private final ApplicationContext applicationContext;
    private final MessageSource messageSource;
    @Value("${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (URIPathUtil.contact(CONTEXT_PATH, "/").startsWith(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        }
        HandlerMethod handlerMethod = null;
        try {
            RequestMappingHandlerMapping req2HandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
            HandlerExecutionChain handlerExeChain = req2HandlerMapping.getHandler(request);
            if (Objects.nonNull(handlerExeChain)) {
                handlerMethod = (HandlerMethod) handlerExeChain.getHandler();
            }
        } catch (Exception e) {
            logger.warn("Lookup the handler method", e);
        } finally {
            logger.debug("URI = " + request.getRequestURI() + ", handlerMethod = " + handlerMethod);
        }
        String bearer = request.getHeader(AUTHORIZATION);
        List<String> errors = new ArrayList<>();
        if (handlerMethod != null) {
            Privileges privilegeAnnotation = handlerMethod.getMethodAnnotation(Privileges.class);
            if (privilegeAnnotation != null) {
                List<String> allowPrivileges = Arrays.asList(privilegeAnnotation.value());
                if (!CollectionUtils.isEmpty(allowPrivileges)) {
                    if (allowPrivileges.contains("")) {
                        filterChain.doFilter(request, response);
                    } else {
                        if (!StringUtils.hasText(bearer)) {
                            errors.add("Token is empty");
                        }
                    }
                }
            } else {
                errors.add("Private");
            }
        }
        if (!CollectionUtils.isEmpty(errors)) {
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            new ObjectMapper().writeValue(
                    response.getOutputStream()
                    , BaseResponseDto.builder()
                            .success(false)
                            .errorCode(HttpStatus.UNAUTHORIZED.value())
                            .message(StringUtils.capitalize(
                                    messageSource.getMessage("UnauthorizedExceptionAdvice", null, LocaleContextHolder.getLocale())))
                            .data(errors)
                            .build());
        }
    }
}