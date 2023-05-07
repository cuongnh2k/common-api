package space.cuongnh2k.rest.auth;

import space.cuongnh2k.rest.auth.dto.AccountRes;
import space.cuongnh2k.rest.auth.dto.LoginReq;
import space.cuongnh2k.rest.auth.dto.LoginRes;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;

public interface AuthService {
    LoginRes login(LoginReq req);

    RefreshTokenRes refreshToken();

   AccountRes getDetailAccount();
}
