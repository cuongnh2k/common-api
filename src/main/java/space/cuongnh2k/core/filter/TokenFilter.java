package space.cuongnh2k.core.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.TokenTypeEnum;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class TokenFilter {
    private final AuthContext authContext;
    private final HttpServletRequest request;

    public String filter() {
        String verifyToken = authContext.setAuth(request.getHeader(AUTHORIZATION));
        if (verifyToken != null) {
            return verifyToken;
        } else {
            if (request.getRequestURI().contains("/auth/refresh-token")) {
                if (!authContext.getTokenType().equals(TokenTypeEnum.REFRESH_TOKEN.toString())) {
                    return "Wrong type of token";
                }
            } else {
                if (!authContext.getTokenType().equals(TokenTypeEnum.ACCESS_TOKEN.toString())) {
                    return "Wrong type of token";
                }
            }
        }
        return null;
    }
}
