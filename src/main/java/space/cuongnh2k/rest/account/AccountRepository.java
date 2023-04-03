package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.query.CreateAccountPrt;
import space.cuongnh2k.rest.account.query.GetAccountRss;

public interface AccountRepository {
    GetAccountRss getOneByEmail(String email);

    int createAccount(CreateAccountPrt prt);
}