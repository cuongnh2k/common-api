package space.cuongnh2k.rest.auth.impl;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.crypto.JwtCrypto;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.core.utils.SendEmailUtil;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.ActivationCodePrt;
import space.cuongnh2k.rest.account.query.*;
import space.cuongnh2k.rest.auth.AuthService;
import space.cuongnh2k.rest.auth.dto.AccountRes;
import space.cuongnh2k.rest.auth.dto.LoginReq;
import space.cuongnh2k.rest.auth.dto.LoginRes;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;
import space.cuongnh2k.rest.device.query.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;
    private final AuthContext authContext;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtCrypto jwtCrypto;
    private final SendEmailUtil sendEmailUtil;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<BaseResponseDto> login(LoginReq req) {
        try {
            daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0006);
        }
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .email(req.getEmail())
                .build());

        String activationCode = UUID.randomUUID().toString();
        if (listAccountRss.get(0).getIsActivated() == IsActivated.NO) {

            ActivationCodePrt activationCodePrt = new Gson().fromJson(listAccountRss.get(0).getActivationCode(), ActivationCodePrt.class);
            if (LocalDateTime.parse(activationCodePrt.getAccount().getCreatedDate())
                    .plusMinutes(5).compareTo(LocalDateTime.now()) < 0) {

                activationCodePrt.setAccount(AccountActivationPrt.builder()
                        .code(activationCode)
                        .createdDate(LocalDateTime.now().toString())
                        .build());
                if (accountRepository.updateAccount(UpdateAccountPrt.builder()
                        .id(listAccountRss.get(0).getId())
                        .activationCode(new Gson().toJson(activationCodePrt))
                        .build()) != 1) {
                    throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0007);
                }
                sendEmailUtil.activateAccount(listAccountRss.get(0).getEmail(), listAccountRss.get(0).getId(), activationCode);
            }
            return BaseResponseDto.error(BusinessLogicEnum.BUSINESS_LOGIC_0008.getMessage(),
                    BusinessLogicEnum.BUSINESS_LOGIC_0008.getErrorCode());
        }
        List<DeviceRss> listDeviceRss = deviceRepository.getDevice(GetDevicePrt.builder()
                .accountId(listAccountRss.get(0).getId())
                .userAgent(request.getHeader(USER_AGENT))
                .build());
        LoginRes loginRes;
        if (CollectionUtils.isEmpty(listDeviceRss)) {
            String deviceId = UUID.randomUUID().toString();
            loginRes = jwtCrypto.encode(listAccountRss.get(0), deviceId);
            if (deviceRepository.createDevice(CreateDevicePrt.builder()
                    .id(deviceId)
                    .accountId(listAccountRss.get(0).getId())
                    .accessToken(loginRes.getAccessToken())
                    .refreshToken(loginRes.getRefreshToken())
                    .userAgent(request.getHeader(USER_AGENT).length() > 255
                            ? request.getHeader(USER_AGENT).substring(request.getHeader(USER_AGENT).length() - 255)
                            : request.getHeader(USER_AGENT))
                    .activationCode(new Gson().toJson(space.cuongnh2k.rest.device.query.ActivationCodePrt.builder()
                            .device(DeviceActivationPrt.builder()
                                    .code(activationCode)
                                    .createdDate(LocalDateTime.now().toString())
                                    .build())
                            .build()))
                    .build()) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0009);
            }
            sendEmailUtil.activateDevice(listAccountRss.get(0).getEmail(), deviceId, activationCode);
        } else {
            loginRes = jwtCrypto.encode(listAccountRss.get(0), listDeviceRss.get(0).getId());
            UpdateDevicePrt devicePrt = UpdateDevicePrt.builder()
                    .id(listDeviceRss.get(0).getId())
                    .accessToken(loginRes.getAccessToken())
                    .refreshToken(loginRes.getRefreshToken())
                    .build();
            if (listDeviceRss.get(0).getIsActivated() == IsActivated.NO) {
                space.cuongnh2k.rest.device.query.ActivationCodePrt activationCodePrt
                        = new Gson().fromJson(listDeviceRss.get(0).getActivationCode(), space.cuongnh2k.rest.device.query.ActivationCodePrt.class);
                if (LocalDateTime.parse(activationCodePrt.getDevice().getCreatedDate())
                        .plusMinutes(5).compareTo(LocalDateTime.now()) < 0) {

                    activationCodePrt.setDevice(DeviceActivationPrt.builder()
                            .code(activationCode)
                            .createdDate(LocalDateTime.now().toString())
                            .build());

                    devicePrt = UpdateDevicePrt.builder()
                            .id(listDeviceRss.get(0).getId())
                            .accessToken(loginRes.getAccessToken())
                            .refreshToken(loginRes.getRefreshToken())
                            .activationCode(new Gson().toJson(activationCodePrt))
                            .build();
                    sendEmailUtil.activateDevice(listAccountRss.get(0).getEmail(),
                            listDeviceRss.get(0).getId(),
                            activationCode);
                }
            }
            if (deviceRepository.updateDevice(devicePrt) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0010);
            }
        }
        return BaseResponseDto.success(loginRes);
    }

    @Override
    public RefreshTokenRes refreshToken() {
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .id(authContext.getAccountId())
                .build());
        RefreshTokenRes refreshTokenRes = jwtCrypto.encode(listAccountRss.get(0), authContext.getBearer(), authContext.getDeviceId());
        if (deviceRepository.updateDevice(UpdateDevicePrt.builder()
                .id(authContext.getDeviceId())
                .accessToken(refreshTokenRes.getAccessToken())
                .build()) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0010);
        }
        return refreshTokenRes;
    }

    @Override
    public AccountRes getDetailAccount() {
        List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                .id(authContext.getAccountId())
                .build());
        AccountRes res = new AccountRes();
        BeanCopyUtil.copyProperties(res, listAccountRss.get(0));
        return res;
    }
}
