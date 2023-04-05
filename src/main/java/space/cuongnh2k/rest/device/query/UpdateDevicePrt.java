package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateDevicePrt {
    private String id;
    private String accessToken;
    private String refreshToken;
    private String activationCode;
}
