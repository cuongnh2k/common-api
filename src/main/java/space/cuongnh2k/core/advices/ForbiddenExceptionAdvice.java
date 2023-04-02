package space.cuongnh2k.core.advices;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.exceptions.ForbiddenException;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ForbiddenExceptionAdvice extends BaseAdvice {

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<BaseResponseDto> exception() {
        String message = StringUtils.capitalize(messageSource.getMessage("ForbiddenExceptionAdvice", null, LocaleContextHolder.getLocale()));
        log.warn(String.format("%s - %s", HttpStatus.FORBIDDEN.value(), message));
        return BaseResponseDto.error(message, HttpStatus.FORBIDDEN.value());
    }
}
