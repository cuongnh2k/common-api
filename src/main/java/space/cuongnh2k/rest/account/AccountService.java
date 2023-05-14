package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.dto.*;

public interface AccountService {
    SearchAccountRes searchAccount(String email, String id);

    void createAccount(CreateAccountReq req);

    void activeAccount(ActiveAccountReq req);

    void updateAccount(UpdateAccountReq req);

    void confirmResetPassword(String id);

    Object getNewPassword(GetNewPasswordReq req);
}
