package space.cuongnh2k.rest.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.auth.dto.LoginReq;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestBody @Valid LoginReq req) {
        return BaseResponseDto.success("Login successful", authService.login(req));
    }

    @Privileges("")
    @PatchMapping("refresh-token")
    public ResponseEntity<BaseResponseDto> refreshToken() {
        return BaseResponseDto.success("Refresh token successful", authService.refreshToken());
    }

    @Privileges("")
    @GetMapping("/check")
    public ResponseEntity<BaseResponseDto> getDetailAccount() {
        return BaseResponseDto.success(authService.getDetailAccount());
    }
}
