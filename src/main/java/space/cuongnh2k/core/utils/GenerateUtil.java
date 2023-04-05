package space.cuongnh2k.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.cuongnh2k.core.enums.TokenTypeEnum;
import space.cuongnh2k.rest.account.dto.LoginRes;
import space.cuongnh2k.rest.account.query.AccountRss;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class GenerateUtil {

    private final HttpServletRequest request;

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.jwt.access-token-age}")
    private Long ACCESS_TOKEN_AGE;

    @Value("${application.jwt.refresh-token-age}")
    private Long REFRESH_TOKEN_AGE;

    public LoginRes generateToken(AccountRss accountRss) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return LoginRes.builder()
                .accessToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.ACCESS_TOKEN.toString())
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.REFRESH_TOKEN.toString())
                        .sign(algorithm))
                .build();
    }
}
