package space.cuongnh2k.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessLogicEnum {
    BUSINESS_LOGIC_0001(-1, "Không tìm thấy tài khoản"),
    BUSINESS_LOGIC_0002(-2, "Tạo tài khoản thất bại"),
    BUSINESS_LOGIC_0003(-3, "Mã xác không chính xác"),
    BUSINESS_LOGIC_0004(-4, "Mã xác thực hết hạn"),
    BUSINESS_LOGIC_0005(-5, "Kích hoạt tài khoản thất bại"),
    BUSINESS_LOGIC_0006(-6, "Mật khẩu cũ không chính xác"),
    BUSINESS_LOGIC_0007(-7, "Cập nhật tài khoản thất bại"),
    BUSINESS_LOGIC_0008(-8, "Lấy mã thiết lập lại mật khẩu thất bại"),
    BUSINESS_LOGIC_0009(-9, "Thiết lập lại mật khẩu thất bại"),
    BUSINESS_LOGIC_0010(-10, "Mật khẩu không chính xác"),
    BUSINESS_LOGIC_0011(-11, "Đăng nhập thất bại"),
    BUSINESS_LOGIC_0012(-12, "Tài khoản chưa kích hoạt"),
    BUSINESS_LOGIC_0013(-13, "Làm mới token thất bại"),
    BUSINESS_LOGIC_0014(-14, "Kích hoạt thiết bị thất bại"),
    BUSINESS_LOGIC_0015(-15, "Đăng xuất thất bại"),
    BUSINESS_LOGIC_0016(-16, "File tải lên trống"),
    BUSINESS_LOGIC_0017(-17, "Tải file lên thất bại"),
    BUSINESS_LOGIC_0018(-18, "Xóa file thất bại"),
    BUSINESS_LOGIC_0019(-19, "Không tìm thấy file"),
    BUSINESS_LOGIC_0020(-20, "Tải xuống file thất bại");

    private final Integer errorCode;
    private final String message;
}
