package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.dto.ActiveAccountReq;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;
import space.cuongnh2k.rest.account.dto.UpdateAccountReq;

public interface AccountService {
    void createAccount(CreateAccountReq req);

    void activeAccount(ActiveAccountReq req);

    void updateAccount(UpdateAccountReq req);
}
