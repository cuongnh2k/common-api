package space.cuongnh2k.rest.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.annotation.Email;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.annotation.UUID;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.account.dto.ActiveAccountReq;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;
import space.cuongnh2k.rest.account.dto.GetNewPasswordReq;
import space.cuongnh2k.rest.account.dto.UpdateAccountReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Privileges("")
    @GetMapping("/{email}")
    public ResponseEntity<BaseResponseDto> searchAccount(@PathVariable @Email String email) {
        return BaseResponseDto.success(accountService.searchAccount(email));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createAccount(@RequestBody @Valid CreateAccountReq req) {
        accountService.createAccount(req);
        return BaseResponseDto.success("Create account successful");
    }

    @PatchMapping("/active")
    public ResponseEntity<BaseResponseDto> activeAccount(@RequestBody @Valid ActiveAccountReq req) {
        accountService.activeAccount(req);
        return BaseResponseDto.success("Active account successful");
    }

    @Privileges("")
    @PatchMapping
    public ResponseEntity<BaseResponseDto> updateAccount(@RequestBody @Valid UpdateAccountReq req) {
        accountService.updateAccount(req);
        return BaseResponseDto.success("Update account successful");
    }

    @PostMapping("{id}/confirm-reset-password")
    public ResponseEntity<BaseResponseDto> confirmResetPassword(@PathVariable @UUID String id) {
        accountService.confirmResetPassword(id);
        return BaseResponseDto.success("");
    }

    @PostMapping("/get-new-password")
    public ResponseEntity<BaseResponseDto> getNewPassword(@RequestBody @Valid GetNewPasswordReq req) {
        return BaseResponseDto.success(accountService.getNewPassword(req));
    }
}
