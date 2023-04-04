package space.cuongnh2k.core.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.cuongnh2k.core.enums.BusinessLogicEnum;

@Getter
@Setter
@NoArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private int errorCode;

    public BusinessLogicException(BusinessLogicEnum businessLogic) {
        super(businessLogic.getMessage());
        this.errorCode = businessLogic.getErrorCode();
    }
}
