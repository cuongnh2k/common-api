package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
@Builder
public class GetDevicePrt {
    private String id;
    private String accountId;
    private String accessToken;
    private String refreshToken;
    private String userAgent;
    private IsActivated isActivated;
}
