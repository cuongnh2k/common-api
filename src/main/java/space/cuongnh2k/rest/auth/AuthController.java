package space.cuongnh2k.rest.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
