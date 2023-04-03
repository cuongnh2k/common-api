package space.cuongnh2k.core.advices;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import space.cuongnh2k.core.base.BaseResponseDto;

import java.sql.SQLException;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class SqlExceptionAdvice extends BaseAdvice{

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<BaseResponseDto> exception(SQLException e) {
        log.warn(String.format("%s - %s", e.getErrorCode(), e.getMessage()));
        return BaseResponseDto.error(e.getMessage(), e.getErrorCode());
    }
}
