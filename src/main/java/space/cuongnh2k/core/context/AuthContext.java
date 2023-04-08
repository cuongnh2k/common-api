package space.cuongnh2k.core.context;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {
    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;
    private String bearer;

    public String getBearer() {
        return this.bearer;
    }

    public void setBearer(String token) {
        try {
            this.bearer = token.substring("Bearer ".length());
        } catch (Exception ignore) {
            this.bearer = null;
        }
    }

    public String getAccountId() {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(bearer).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getTokenType() {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(bearer).getClaim("type").asString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getDeviceId() {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY.getBytes())).build().verify(bearer).getClaim("deviceId").asString();
        } catch (Exception e) {
            return null;
        }
    }
}
