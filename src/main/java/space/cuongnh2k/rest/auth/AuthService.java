package space.cuongnh2k.rest.auth;

import org.springframework.http.ResponseEntity;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.auth.dto.AccountRes;
import space.cuongnh2k.rest.auth.dto.LoginReq;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;

public interface AuthService {
    ResponseEntity<BaseResponseDto> login(LoginReq req);

    RefreshTokenRes refreshToken();

    AccountRes getDetailAccount();
}
