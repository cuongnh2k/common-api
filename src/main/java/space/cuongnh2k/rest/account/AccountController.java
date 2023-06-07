package space.cuongnh2k.rest.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.annotation.UUID;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.account.dto.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Privileges("")
    @PostMapping("/extract")
    public ResponseEntity<BaseResponseDto> extractAccount(@RequestBody @Valid ExtractAccountReq req) {
        return BaseResponseDto.success(accountService.extractAccount(req));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createAccount(@RequestBody @Valid CreateAccountReq req) {
        accountService.createAccount(req);
        return BaseResponseDto.success("Tạo tài khoản thành công");
    }

    @PatchMapping("/active")
    public ResponseEntity<BaseResponseDto> activeAccount(@RequestBody @Valid ActiveAccountReq req) {
        accountService.activeAccount(req);
        return BaseResponseDto.success("Kích hoạt tài khoản thành công");
    }

    @Privileges("")
    @PatchMapping
    public ResponseEntity<BaseResponseDto> updateAccount(@RequestBody @Valid UpdateAccountReq req) {
        accountService.updateAccount(req);
        return BaseResponseDto.success("Cập nhật tài khoản thành công");
    }

    @PostMapping("{id}/confirm-reset-password")
    public ResponseEntity<BaseResponseDto> confirmResetPassword(@PathVariable @UUID String id) {
        accountService.confirmResetPassword(id);
        return BaseResponseDto.success("Một email chứa mã xác nhận thiết lập lại mật khẩu đã được gửi cho bạn");
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<BaseResponseDto> confirmResetPasswordV1(@RequestParam @UUID String accountId) {
        accountService.confirmResetPassword(accountId);
        return BaseResponseDto.success("Một email chứa mã xác nhận thiết lập lại mật khẩu đã được gửi cho bạn");
    }

    @PostMapping("/get-new-password")
    public ResponseEntity<BaseResponseDto> getNewPassword(@RequestBody @Valid GetNewPasswordReq req) {
        return BaseResponseDto.success(accountService.getNewPassword(req));
    }
}
