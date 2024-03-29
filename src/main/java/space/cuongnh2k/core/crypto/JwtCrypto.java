package space.cuongnh2k.core.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.cuongnh2k.core.enums.TokenTypeEnum;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.account.dto.AccountRes;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.auth.dto.LoginRes;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;

import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtCrypto {
    private final HttpServletRequest request;

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.jwt.access-token-age}")
    private Long ACCESS_TOKEN_AGE;

    @Value("${application.jwt.refresh-token-age}")
    private Long REFRESH_TOKEN_AGE;

    public LoginRes encode(AccountRss accountRss, String deviceId) {
        AccountRes accountRes = new AccountRes();
        BeanCopyUtil.copyProperties(accountRes, accountRss);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return LoginRes.builder()
                .accessToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.ACCESS_TOKEN.toString())
                        .withClaim("deviceId", deviceId)
                        .withClaim("account", new ObjectMapper().convertValue(accountRes, Map.class))
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.REFRESH_TOKEN.toString())
                        .withClaim("deviceId", deviceId)
                        .sign(algorithm))
                .build();
    }

    public RefreshTokenRes encode(AccountRss accountRss, String refreshToken, String deviceId) {
        AccountRes accountRes = new AccountRes();
        BeanCopyUtil.copyProperties(accountRes, accountRss);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return RefreshTokenRes.builder()
                .accessToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.ACCESS_TOKEN.toString())
                        .withClaim("deviceId", deviceId)
                        .withClaim("account", new ObjectMapper().convertValue(accountRes, Map.class))
                        .sign(algorithm))
                .refreshToken(refreshToken)
                .build();
    }
}
