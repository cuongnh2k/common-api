package space.cuongnh2k.core.context;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import space.cuongnh2k.core.enums.TokenTypeEnum;

@Log4j2
@Getter
@Component
@RequestScope
public class AuthContext {
    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;
    private String bearer;
    private String accountId;
    private String tokenType;
    private String deviceId;
    private AccountContext account;

    public String setAuth(String token) {
        try {
            String bearer = token.substring("Bearer ".length());
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(bearer);
            this.bearer = bearer;
            this.accountId = decodedJWT.getSubject();
            this.tokenType = decodedJWT.getClaim("type").asString();
            this.deviceId = decodedJWT.getClaim("deviceId").asString();
            if (TokenTypeEnum.ACCESS_TOKEN.name().equals(this.tokenType)) {
                this.account = new Gson().fromJson(decodedJWT.getClaim("account").toString(), AccountContext.class);
            }
        } catch (Exception e) {
            log.error(e);
            return e.getMessage();
        }
        return null;
    }
}
