package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Tạo tài khoản thất bại"),
    BUSINESS_LOGIC_0002(-2, "Tài khoản không tồn tại"),
    BUSINESS_LOGIC_0003(-3, "Sai mật khẩu"),
    BUSINESS_LOGIC_0004(-4, "Thêm thiết bị thất bại"),
    BUSINESS_LOGIC_0005(-5, "Cập nhật thiết bị thất bại"),
    BUSINESS_LOGIC_0006(-6, "Thiết bị chưa kích hoạt"),
    BUSINESS_LOGIC_0007(-7, "Kích hoạt tài khoản thất bại"),
    BUSINESS_LOGIC_0008(-8, "Kích hoạt thiết bị thất bại"),
    BUSINESS_LOGIC_0009(-9, "Làm mới token thất bại");
    private final Integer errorCode;
    private final String message;
}
