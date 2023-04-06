package space.cuongnh2k.core.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.enums.TokenTypeEnum;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.auth.dto.LoginRes;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;

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

    public LoginRes encode(AccountRss accountRss) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return LoginRes.builder()
                .accessToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.ACCESS_TOKEN.toString())
                        .withClaim("userAgent", request.getHeader(USER_AGENT))
                        .sign(algorithm))
                .refreshToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.REFRESH_TOKEN.toString())
                        .withClaim("userAgent", request.getHeader(USER_AGENT))
                        .sign(algorithm))
                .build();
    }

    public RefreshTokenRes encode(AccountRss accountRss, String refreshToken) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return RefreshTokenRes.builder()
                .accessToken(JWT.create()
                        .withSubject(accountRss.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("type", TokenTypeEnum.ACCESS_TOKEN.toString())
                        .withClaim("userAgent", request.getHeader(USER_AGENT))
                        .sign(algorithm))
                .refreshToken(refreshToken)
                .build();
    }

    public List<String> decode(TokenTypeEnum type) {
        List<String> listError = new ArrayList<>();
        try {
            String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(token);
            if (!decodedJWT.getClaim("type").asString().equals(type.toString())) {
                listError.add("Incorrect token type");
            }
            if (!decodedJWT.getClaim("userAgent").asString().equals(request.getHeader(USER_AGENT))) {
                listError.add("Incorrect userAgent");
            }
        } catch (Exception exception) {
            listError.add(exception.getMessage());
        }
        if (CollectionUtils.isEmpty(listError)) {
            return null;
        }
        return listError;
    }
}
