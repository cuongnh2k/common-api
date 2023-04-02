package space.cuongnh2k.core.advices;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.exceptions.BusinessLogicException;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class BusinessLogicAdvice {

    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<BaseResponseDto> exception(BusinessLogicException exception) {
        String location = "";
        for (StackTraceElement StackTraceElement : exception.getStackTrace()) {
            String controllerName = StackTraceElement.getClassName();
            if (controllerName.endsWith("Controller")) {
                location = String.join(".",
                        controllerName, StackTraceElement.getMethodName());
                break;
            }
        }
        log.warn(String.format("%s - %s - %s", exception.getErrorCode(), location, exception.getMessage()));
        return BaseResponseDto.error(exception.getMessage(), exception.getErrorCode());
    }
}
