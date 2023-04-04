package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Account registration failed"),
    BUSINESS_LOGIC_0002(-2, "Account does not exist"),
    BUSINESS_LOGIC_0003(-3, "Wrong password");
    private final Integer errorCode;
    private final String message;
}
