package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Không tìm thấy tài khoản"),
    BUSINESS_LOGIC_0002(-2, "Tạo tài khoản thất bại"),
    BUSINESS_LOGIC_0003(-3, "Mã xác thực chỉ có hiệu lực trong 5'"),
    BUSINESS_LOGIC_0004(-4, "Mã xác thực không chính xác"),
    BUSINESS_LOGIC_0005(-5, "Kích hoạt tài khoản thất bại"),
    BUSINESS_LOGIC_0006(-6, "Mật khẩu không chính xác"),
    BUSINESS_LOGIC_0007(-7, "Cập nhật tài khoản thất bại"),
    BUSINESS_LOGIC_0008(-8, "Tài khoản chưa được kích hoạt"),
    BUSINESS_LOGIC_0009(-9, "Tạo thiết bị thất bại"),
    BUSINESS_LOGIC_0010(-10, "Cập nhật thiết bị"),
    BUSINESS_LOGIC_0011(-11, ""),
    BUSINESS_LOGIC_0012(-12, ""),
    BUSINESS_LOGIC_0013(-13, ""),
    BUSINESS_LOGIC_0014(-14, ""),
    BUSINESS_LOGIC_0015(-15, ""),
    BUSINESS_LOGIC_0016(-16, "");

    private final Integer errorCode;
    private final String message;
}
