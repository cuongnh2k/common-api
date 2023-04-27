package space.cuongnh2k.rest.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.account.dto.ActiveAccountReq;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;
import space.cuongnh2k.rest.account.dto.UpdateAccountReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

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
}
