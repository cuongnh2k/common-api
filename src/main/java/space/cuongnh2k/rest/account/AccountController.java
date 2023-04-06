package space.cuongnh2k.rest.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.account.dto.ActiveAccountReq;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Privileges("")
    @PostMapping
    public ResponseEntity<BaseResponseDto> createAccount(@RequestBody @Valid CreateAccountReq req) {
        accountService.createAccount(req);
        return BaseResponseDto.success("Create account successful");
    }

    @Privileges("")
    @PostMapping("/active")
    public ResponseEntity<BaseResponseDto> activeAccount(@RequestBody @Valid ActiveAccountReq req) {
        accountService.activeAccount(req);
        return BaseResponseDto.success("Active account successful");
    }
}
