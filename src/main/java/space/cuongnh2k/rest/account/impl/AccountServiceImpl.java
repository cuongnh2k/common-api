package space.cuongnh2k.rest.account.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.core.utils.RandomUtil;
import space.cuongnh2k.core.utils.SendEmailUtil;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.AccountService;
import space.cuongnh2k.rest.account.dto.*;
import space.cuongnh2k.rest.account.query.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final SendEmailUtil sendEmailUtil;
    private final AuthContext authContext;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    @Value("${application.activation-code-age}")
    private Integer ACTIVATION_CODE_AGE;

    @Override
    public ExtractAccountRes extractAccount(ExtractAccountReq req) {
        List<AccountRss> rss = accountRepository.getAccount(GetAccountPrt.builder()
                .emails(req.getEmails())
                .ids(req.getIds())
                .isActivated(IsActivated.YES)
                .build());
        if (CollectionUtils.isEmpty(rss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0001);
        }
        return rss.stream()
                .findFirst()
                .map(o -> {
                    ExtractAccountRes res = new ExtractAccountRes();
                    BeanCopyUtil.copyProperties(res, o);
                    return res;
                }).orElse(null);
    }

    @Override
    public void createAccount(CreateAccountReq req) {
        CreateAccountPrt prt = new CreateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setId(UUID.randomUUID().toString());

        String code = UUID.randomUUID().toString();
        ActivationCodePrt activationCodePrt = ActivationCodePrt.builder()
                .account(AccountActivationPrt.builder()
                        .code(code)
                        .createdTime(LocalDateTime.now().toString())
                        .build())
                .resetPassword(ResetPasswordActivationPrt.builder()
                        .code(code)
                        .createdTime(LocalDateTime.now().toString())
                        .build())
                .build();
        prt.setActivationCode(new Gson().toJson(activationCodePrt));
        prt.setPassword(passwordEncoder.encode(prt.getPassword()));
        if (accountRepository.createAccount(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0002);
        }
        sendEmailUtil.activateAccount(prt.getEmail(), prt.getId(), code);
    }

    @Override
    public void activeAccount(ActiveAccountReq req) {
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .ids(Collections.singletonList(req.getId()))
                .build());
        if (CollectionUtils.isEmpty(listAccountRss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0003);
        }

        ActivationCodePrt activationCodePrt = new Gson().fromJson(listAccountRss.get(0).getActivationCode(), ActivationCodePrt.class);
        if (LocalDateTime.parse(activationCodePrt.getAccount().getCreatedTime())
                .plusMinutes(ACTIVATION_CODE_AGE).compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0004);
        }
        if (!activationCodePrt.getAccount().getCode().equals(req.getActivationCode())) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0003);
        }
        if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                .id(listAccountRss.get(0).getId())
                .isActivated(IsActivated.YES)
                .build()) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0005);
        }
    }

    @Override
    public void updateAccount(UpdateAccountReq req) {
        List<AccountRss> rss = accountRepository.getAccount(GetAccountPrt.builder()
                .ids(Collections.singletonList(authContext.getAccountId()))
                .build());
        if (CollectionUtils.isEmpty(rss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0001);
        }

        UpdateAccountPrt prt = new UpdateAccountPrt();
        BeanCopyUtil.copyProperties(prt, req);

        if (prt.getPassword() != null) {
            try {
                daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(rss.get(0).getEmail(), req.getPasswordOld()));
            } catch (BadCredentialsException e) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0006);
            }
            prt.setPassword(passwordEncoder.encode(prt.getPassword()));
        }

        prt.setId(authContext.getAccountId());
        if (accountRepository.updateAccount(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0007);
        }
    }

    @Override
    public void confirmResetPassword(String id) {
        List<AccountRss> rss = accountRepository.getAccount(GetAccountPrt.builder()
                .ids(Collections.singletonList(id))
                .build());
        if (CollectionUtils.isEmpty(rss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0001);
        }
        ActivationCodePrt activationCodePrt = new Gson().fromJson(rss.get(0).getActivationCode(), ActivationCodePrt.class);

        if (LocalDateTime.parse(activationCodePrt.getResetPassword().getCreatedTime())
                .plusMinutes(ACTIVATION_CODE_AGE).compareTo(LocalDateTime.now()) < 0) {

            String code = UUID.randomUUID().toString();
            activationCodePrt.setResetPassword(ResetPasswordActivationPrt.builder()
                    .code(code)
                    .createdTime(LocalDateTime.now().toString())
                    .build());

            if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                    .id(rss.get(0).getId())
                    .activationCode(new Gson().toJson(activationCodePrt))
                    .build()) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0008);
            }
            sendEmailUtil.confirmResetPassword(rss.get(0).getEmail(), rss.get(0).getId(), code);
        }
    }

    @Override
    public Object getNewPassword(GetNewPasswordReq req) {
        List<AccountRss> rss = accountRepository.getAccount(GetAccountPrt.builder()
                .ids(Collections.singletonList(req.getId()))
                .build());
        if (CollectionUtils.isEmpty(rss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0003);
        }

        ActivationCodePrt activationCodePrt = new Gson().fromJson(rss.get(0).getActivationCode(), ActivationCodePrt.class);
        if (LocalDateTime.parse(activationCodePrt.getResetPassword().getCreatedTime())
                .plusMinutes(ACTIVATION_CODE_AGE).compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0004);
        }

        if (!activationCodePrt.getResetPassword().getCode().equals(req.getActivationCode())) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0003);
        }

        String password = new RandomUtil().randomPassword();
        if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                .password(passwordEncoder.encode(password))
                .id(req.getId())
                .build()) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0009);
        }
        return password;
    }
}
