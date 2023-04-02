package space.cuongnh2k.core.advices;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.cuongnh2k.core.base.BaseResponseDto;

import java.util.*;

@Log4j2
@ControllerAdvice
public class ConstraintViolationAdvice extends BaseAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<BaseResponseDto> exception(ConstraintViolationException exception) {

        Map<String, List<String>> data = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            Iterator<Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
            Path.Node node = iterator.next();
            while (iterator.hasNext()) {
                node = iterator.next();
            }
            String name = node.getName();

            if (!data.containsKey(name)) {
                data.put(name, new ArrayList<>());
            }
            List<String> listErrorMessages = data.get(name);
            listErrorMessages.add(constraintViolation.getMessage());
        }
        String message = StringUtils.capitalize(messageSource.getMessage("ConstraintViolationAdvice", null, LocaleContextHolder.getLocale()));
        log.warn(String.format("%s - %s - %s", HttpStatus.BAD_REQUEST.value(), message, data));
        return BaseResponseDto.error(message, HttpStatus.BAD_REQUEST.value(), data);
    }
}
