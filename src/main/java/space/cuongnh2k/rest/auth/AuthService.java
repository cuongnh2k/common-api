package space.cuongnh2k.rest.auth;

import space.cuongnh2k.rest.auth.dto.LoginReq;
import space.cuongnh2k.rest.auth.dto.LoginRes;

public interface AuthService {
    LoginRes login(LoginReq req);
}
