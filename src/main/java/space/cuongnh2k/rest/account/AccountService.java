package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.dto.ActiveAccountReq;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;

public interface AccountService {
    void createAccount(CreateAccountReq req);
    void activeAccount(ActiveAccountReq req);
}
