package space.cuongnh2k.rest.account.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.AccountService;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;
import space.cuongnh2k.rest.account.query.CreateAccountPrt;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    @Override
    public void createAccount(CreateAccountReq req) {
        CreateAccountPrt prt = new CreateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setId(UUID.randomUUID().toString());
        prt.setPassword(passwordEncoder.encode(prt.getPassword()));
        if (accountRepository.createAccount(prt) != 1) {
            throw new BusinessLogicException();
        }
    }
}
