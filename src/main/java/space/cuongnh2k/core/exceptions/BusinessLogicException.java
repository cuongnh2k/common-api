package space.cuongnh2k.core.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLogicException extends RuntimeException {
    private Integer errorCode;

    public BusinessLogicException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
