package space.cuongnh2k.core.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import space.cuongnh2k.core.crypto.JwtCrypto;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.enums.TokenTypeEnum;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.query.DeviceRss;
import space.cuongnh2k.rest.device.query.GetDevicePrt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Log4j2
@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {
    private final ApplicationContext applicationContext;
    private final MessageSource messageSource;
    private final JwtCrypto jwtCrypto;
    private final DeviceRepository deviceRepository;
    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
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
        List<String> listError = new ArrayList<>();
        if (handlerMethod != null) {
            Privileges privilegeAnnotation = handlerMethod.getMethodAnnotation(Privileges.class);
            if (privilegeAnnotation != null) {
                String bearer = request.getHeader(AUTHORIZATION);
                if (!StringUtils.hasText(bearer)) {
                    listError.add("Token is empty");
                } else {
                    List<String> resultDecode;
                    if (request.getRequestURI().contains("/device/refresh-token")) {
                        resultDecode = jwtCrypto.decode(TokenTypeEnum.REFRESH_TOKEN);
                    } else {
                        resultDecode = jwtCrypto.decode(TokenTypeEnum.ACCESS_TOKEN);
                    }
                    if (resultDecode != null) {
                        listError.addAll(resultDecode);
                    } else {
                        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
                        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(token);
                        List<DeviceRss> listDeviceRss = deviceRepository.getDevice(GetDevicePrt.builder()
                                .accountId(decodedJWT.getSubject())
                                .userAgent(request.getHeader(USER_AGENT))
                                .build());
                        if (CollectionUtils.isEmpty(listDeviceRss)) {
                            listError.add("Thiết bị đã đăng xuất");
                        } else if (listDeviceRss.get(0).getIsActivated() == IsActivated.NO) {
                            listError.add("Thiết bị chưa được kích hoạt");
                        }
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(listError)) {
            new ObjectMapper().writeValue(
                    response.getOutputStream()
                    , BaseResponseDto.builder()
                            .success(false)
                            .errorCode(HttpStatus.UNAUTHORIZED.value())
                            .message(StringUtils.capitalize(
                                    messageSource.getMessage("UnauthorizedExceptionAdvice", null, LocaleContextHolder.getLocale())))
                            .data(listError)
                            .build());
        }
        filterChain.doFilter(request, response);
    }
}