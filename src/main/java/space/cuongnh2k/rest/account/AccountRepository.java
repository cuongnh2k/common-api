package space.cuongnh2k.rest.account;

import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.CreateAccountPrt;
import space.cuongnh2k.rest.account.query.GetAccountPrt;

import java.util.List;

public interface AccountRepository {
    List<AccountRss> getAccount(GetAccountPrt prt);

    int createAccount(CreateAccountPrt prt);
}