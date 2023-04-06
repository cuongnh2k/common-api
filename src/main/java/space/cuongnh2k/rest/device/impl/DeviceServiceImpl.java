package space.cuongnh2k.rest.device.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.cuongnh2k.core.crypto.JwtCrypto;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.GetAccountPrt;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.DeviceService;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;
import space.cuongnh2k.rest.device.query.UpdateDevicePrt;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final AccountRepository accountRepository;
    private final HttpServletRequest request;
    private final JwtCrypto jwtCrypto;
    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    public void activeDevice(ActiveDeviceReq req) {
        UpdateDevicePrt prt = new UpdateDevicePrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setIsActivated(IsActivated.YES);
        if (deviceRepository.updateDevice(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0008);
        }
    }

    @Override
    public RefreshTokenRes refreshToken() {
        try {
            String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(token);
            List<AccountRss> listAccountRss = accountRepository.getAccount(GetAccountPrt.builder()
                    .id(decodedJWT.getSubject()).build());
            RefreshTokenRes refreshTokenRes = jwtCrypto.encode(listAccountRss.get(0), token);
            if (deviceRepository.updateDevice(UpdateDevicePrt.builder()
                    .id(decodedJWT.getId())
                    .accessToken(refreshTokenRes.getAccessToken())
                    .build()) != 1) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0009);
            }
            return refreshTokenRes;
        } catch (Exception e) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0009);
        }
    }
}
