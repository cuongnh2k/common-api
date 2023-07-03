package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.dto.*;

import java.util.List;

public interface AccountService {
    ExtractAccountRes extractAccount(ExtractAccountReq req);

    void createAccount(CreateAccountReq req);

    void activeAccount(ActiveAccountReq req);

    void updateAccount(UpdateAccountReq req);

    void confirmResetPassword(String id);

    Object getNewPassword(GetNewPasswordReq req);
}
