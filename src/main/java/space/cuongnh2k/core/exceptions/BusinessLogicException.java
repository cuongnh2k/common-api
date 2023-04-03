package space.cuongnh2k.core.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private int errorCode;

    public BusinessLogicException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
