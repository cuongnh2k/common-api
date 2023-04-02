package space.cuongnh2k.core.advices;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.cuongnh2k.core.base.BaseResponseDto;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@Log4j2
@ControllerAdvice
public class HttpMessageNotReadableAdvice extends BaseAdvice {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<BaseResponseDto> exception(HttpMessageNotReadableException exception) {
        final Throwable cause = exception.getCause();
        Map<String, List<String>> data = new HashMap<>();
        if (cause instanceof JsonMappingException jsonMappingException) {
            List<JsonMappingException.Reference> references = jsonMappingException.getPath();
            for (JsonMappingException.Reference reference : references) {
                String fieldName = reference.getFieldName();
                if (fieldName != null) {
                    List<String> listErrMsg = new ArrayList<>();
                    data.put(fieldName, listErrMsg);

                    Field field = ReflectionUtils.findField(reference.getFrom().getClass(), fieldName);
                    Class<?> type = field.getType();

                    if (type.equals(Long.class) || type.equals(Integer.class)) {
                        listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                                new Object[]{"a integer number"}, LocaleContextHolder.getLocale()));
                    } else if (type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class)) {
                        listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                                new Object[]{"a real number"}, LocaleContextHolder.getLocale()));
                    } else if (type.equals(Date.class)) {
                        listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                                new Object[]{"a date"}, LocaleContextHolder.getLocale()));
                    } else if (type.isEnum()) {
                        listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                                new Object[]{Arrays.toString(type.getEnumConstants())}, LocaleContextHolder.getLocale()));
                    } else {
                        listErrMsg.add(messageSource.getMessage(MESSAGE_CODE_ERROR_MISMATCH,
                                new Object[]{type.getSimpleName()}, LocaleContextHolder.getLocale()));
                    }
                }
            }
        }
        String message = StringUtils.capitalize(messageSource.getMessage("HttpMessageNotReadableAdvice", null, LocaleContextHolder.getLocale()));
        log.warn(String.format("%s - %s - %s", HttpStatus.BAD_REQUEST.value(), message, data));
        return BaseResponseDto.error(message, HttpStatus.BAD_REQUEST.value(), data);
    }
}
