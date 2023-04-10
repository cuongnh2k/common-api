package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Account creation failed"),
    BUSINESS_LOGIC_0002(-2, "Account does not exist"),
    BUSINESS_LOGIC_0003(-3, "Incorrect password"),
    BUSINESS_LOGIC_0006(-6, "Account not activated"),
    BUSINESS_LOGIC_0007(-7, "Account activation failed"),
    BUSINESS_LOGIC_0011(-11, "Account update failed"),

    BUSINESS_LOGIC_0004(-4, "Add device failed"),
    BUSINESS_LOGIC_0005(-5, "Device update failed"),
    BUSINESS_LOGIC_0008(-8, "Device activation failed"),
    BUSINESS_LOGIC_0009(-9, "Refresh token failed"),
    BUSINESS_LOGIC_0010(-10, "Logout failed"),

    BUSINESS_LOGIC_0012(-12, "Empty file"),
    BUSINESS_LOGIC_0013(-13, "File upload failed");

    private final Integer errorCode;
    private final String message;
}
