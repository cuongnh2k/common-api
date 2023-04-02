package space.cuongnh2k.core.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import space.cuongnh2k.core.base.BaseResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class MethodArgumentNotValidAdvice extends BaseAdvice {

    private static final String MESSAGE_CODE_RESPONSE_MESSAGE = "MethodArgumentNotValidAdvice";

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Nullable
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponseDto> handleException(MethodArgumentNotValidException exception, WebRequest request) {

        LOGGER.warn(String.format("%s - %s", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        LOGGER.warn(exception.getMessage());

        // TODO Handle error of nested json fields

        Map<String, ArrayList<String>> data = new HashMap<>();
        List<FieldError> listFieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError : listFieldErrors) {
            if (!data.containsKey(fieldError.getField())) {
                data.put(fieldError.getField(), new ArrayList<>());
            }
            List<String> listErrorMessages = data.get(fieldError.getField());
            listErrorMessages.add(fieldError.getDefaultMessage());
        }

        BaseResponseDto res = new BaseResponseDto();
        res.setErrorCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(StringUtils.capitalize(
                messageSource.getMessage(MESSAGE_CODE_RESPONSE_MESSAGE, null, LocaleContextHolder.getLocale())));
        res.setData(data);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("code", res.getErrorCode() + "");

        return new ResponseEntity<>(res, responseHeaders, HttpStatus.BAD_REQUEST);
    }
}