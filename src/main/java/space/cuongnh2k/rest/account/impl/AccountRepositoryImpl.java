package space.cuongnh2k.rest.account.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.CreateAccountPrt;
import space.cuongnh2k.rest.account.query.GetAccountRss;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final SqlSession sqlSession;

    @Override
    public GetAccountRss getOneByEmail(String email) {
        return sqlSession.selectOne("space.cuongnh2k.rest.account.AccountRepository.getOneByEmail", email);
    }

    @Override
    public int createAccount(CreateAccountPrt prt) {
        return sqlSession.insert("space.cuongnh2k.rest.account.AccountRepository.createAccount", prt);
    }
}
