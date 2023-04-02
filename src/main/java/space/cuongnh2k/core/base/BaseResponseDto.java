package space.cuongnh2k.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static ResponseEntity<BaseResponseDto> error(String message, Integer errorCode) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build(), HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto> error(String message, Integer errorCode, Object data) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .message(message).build(), HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto> success(String message) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .success(true)
                .message(message).build(), HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto> success(Object data) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .success(true)
                .data(data).build(), HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto> success(String message, Object data) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .success(true)
                .message(message)
                .data(data).build(), HttpStatus.OK);
    }
}
