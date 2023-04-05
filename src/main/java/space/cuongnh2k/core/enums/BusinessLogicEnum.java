package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Account registration failed"),
    BUSINESS_LOGIC_0002(-2, "Account does not exist"),
    BUSINESS_LOGIC_0003(-3, "Wrong password"),
    BUSINESS_LOGIC_0004(-4, "Add device fail"),
    BUSINESS_LOGIC_0005(-5, "Update device fail"),
    BUSINESS_LOGIC_0006(-6, "Account is not activated"),
    BUSINESS_LOGIC_0007(-7, "Account activation failed"),
    BUSINESS_LOGIC_0008(-8, "Device activation failed");
    private final Integer errorCode;
    private final String message;
}
