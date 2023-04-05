package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDevicePrt {
    private String id;
    private String accountId;
    private String accessToken;
    private String refreshToken;
    private String userAgent;
    private String activationCode;
}
