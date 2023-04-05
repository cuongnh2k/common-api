package space.cuongnh2k.rest.account.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.CreateAccountPrt;
import space.cuongnh2k.rest.account.query.GetAccountPrt;
import space.cuongnh2k.rest.account.query.UpdateAccountPrt;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final SqlSession sqlSession;

    @Override
    public List<AccountRss> getAccount(GetAccountPrt prt) {
        return sqlSession.selectList("space.cuongnh2k.rest.account.AccountRepository.getAccount", prt);
    }

    @Override
    public int createAccount(CreateAccountPrt prt) {
        return sqlSession.insert("space.cuongnh2k.rest.account.AccountRepository.createAccount", prt);
    }

    @Override
    public int updateAccount(UpdateAccountPrt prt) {
        return sqlSession.update("space.cuongnh2k.rest.account.AccountRepository.updateAccount",prt);
    }
}
