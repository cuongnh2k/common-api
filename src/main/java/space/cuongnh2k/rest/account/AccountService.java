package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.dto.CreateAccountReq;
import space.cuongnh2k.rest.account.dto.LoginReq;
import space.cuongnh2k.rest.account.dto.LoginRes;

public interface AccountService {
    void createAccount(CreateAccountReq req);

    LoginRes login(LoginReq req);
}
