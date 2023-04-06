package space.cuongnh2k.rest.auth.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.config.SendEmailUtil;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.GetAccountPrt;
import space.cuongnh2k.rest.auth.AuthService;
import space.cuongnh2k.rest.auth.dto.LoginReq;
import space.cuongnh2k.rest.auth.dto.LoginRes;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.query.CreateDevicePrt;
import space.cuongnh2k.rest.device.query.DeviceRss;
import space.cuongnh2k.rest.device.query.GetDevicePrt;
import space.cuongnh2k.rest.device.query.UpdateDevicePrt;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final GenerateUtil generateUtil;
    private final SendEmailUtil sendEmailUtil;
    private final HttpServletRequest request;

    @Override
    public LoginRes login(LoginReq req) {
        try {
            daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0003);
        }
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder().email(req.getEmail()).build());
        if (listAccountRss.get(0).getIsActivated() == IsActivated.NO) {
            sendEmailUtil.activateAccount(listAccountRss.get(0).getEmail(), listAccountRss.get(0).getId(), listAccountRss.get(0).getActivationCode());
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0006);
        }
        List<DeviceRss> listDeviceRss = deviceRepository.getDevice(GetDevicePrt.builder()
                .accountId(listAccountRss.get(0).getId())
                .userAgent(request.getHeader(USER_AGENT))
                .build());
        LoginRes loginRes = generateUtil.generateToken(listAccountRss.get(0));
        String activationCode = UUID.randomUUID().toString();
        String deviceId = UUID.randomUUID().toString();
        if (CollectionUtils.isEmpty(listDeviceRss)) {
            if (deviceRepository.createDevice(CreateDevicePrt.builder()
                    .id(deviceId)
                    .accountId(listAccountRss.get(0).getId())
                    .accessToken(loginRes.getAccessToken())
                    .refreshToken(loginRes.getRefreshToken())
                    .userAgent(request.getHeader(USER_AGENT))
                    .activationCode(activationCode)
                    .build()) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0004);
            }
            sendEmailUtil.activateDevice(listAccountRss.get(0).getEmail(), deviceId, activationCode);
        } else {
            if (deviceRepository.updateDevice(UpdateDevicePrt.builder()
                    .id(listDeviceRss.get(0).getId())
                    .accessToken(loginRes.getAccessToken())
                    .refreshToken(loginRes.getRefreshToken())
                    .activationCode(activationCode)
                    .build()) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0005);
            }
            sendEmailUtil.activateDevice(listAccountRss.get(0).getEmail(), listDeviceRss.get(0).getId(), activationCode);
        }
        return loginRes;
    }
}
