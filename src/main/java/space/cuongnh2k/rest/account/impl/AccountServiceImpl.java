package space.cuongnh2k.rest.account.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.core.utils.SendEmailUtil;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.AccountService;
import space.cuongnh2k.rest.account.dto.*;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.CreateAccountPrt;
import space.cuongnh2k.rest.account.query.GetAccountPrt;
import space.cuongnh2k.rest.account.query.UpdateAccountPrt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final SendEmailUtil sendEmailUtil;
    private final AuthContext authContext;

    @Override
    public SearchAccountRes searchAccount(String email) {
        List<AccountRss> rss = accountRepository.getAccount(GetAccountPrt.builder().email(email).build());
        if (CollectionUtils.isEmpty(rss)) {
            throw new BusinessLogicException();
        }
        SearchAccountRes res = new SearchAccountRes();
        BeanCopyUtil.copyProperties(res, rss.get(0));
        return res;
    }

    @Override
    public void createAccount(CreateAccountReq req) {
        CreateAccountPrt prt = new CreateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setId(UUID.randomUUID().toString());
        String activationCode = UUID.randomUUID().toString();
        prt.setActivationCode(activationCode);
        prt.setPassword(passwordEncoder.encode(prt.getPassword()));
        if (accountRepository.createAccount(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0001);
        }
        sendEmailUtil.activateAccount(prt.getEmail(), prt.getId(), activationCode);
    }

    @Override
    public void activeAccount(ActiveAccountReq req) {
        UpdateAccountPrt prt = new UpdateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setIsActivated(IsActivated.YES);
        if (accountRepository.updateAccount(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0007);
        }
    }

    @Override
    public void updateAccount(UpdateAccountReq req) {
        UpdateAccountPrt prt = new UpdateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setId(authContext.getAccountId());
        prt.setPassword(passwordEncoder.encode(prt.getPassword()));
        if (accountRepository.updateAccount(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0011);
        }
    }

    @Override
    public void confirmResetPassword(String id) {
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .id(id)
                .build());
        if (CollectionUtils.isEmpty(listAccountRss)) {
            throw new BusinessLogicException();
        }
        if (listAccountRss.get(0).getUpdatedDate().plusMinutes(5).compareTo(LocalDateTime.now()) < 0) {
            String code = UUID.randomUUID().toString();
            if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                    .id(listAccountRss.get(0).getId())
                    .activationCodeUseUpdate(code)
                    .build()) != 1) {
                throw new BusinessLogicException();
            }
            sendEmailUtil.confirmResetPassword(listAccountRss.get(0).getEmail(), listAccountRss.get(0).getId(), code);
        }
    }

    @Override
    public Object getNewPassword(GetNewPasswordReq req) {
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .id(req.getId())
                .build());
        if (CollectionUtils.isEmpty(listAccountRss)) {
            throw new BusinessLogicException();
        }
        if (listAccountRss.get(0).getUpdatedDate().plusMinutes(5).compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessLogicException();
        }
        String password = UUID.randomUUID().toString();
        if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                .password(passwordEncoder.encode(password))
                .id(req.getId())
                .activationCode(req.getActivationCode())
                .build()) != 1) {
            throw new BusinessLogicException();
        }
        return password;
    }
}
