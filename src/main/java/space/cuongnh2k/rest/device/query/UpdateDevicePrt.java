package space.cuongnh2k.rest.device.query;

import lombok.*;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDevicePrt {
    private String id;
    private String accessToken;
    private String refreshToken;
    private String activationCode;
    private String activationCodeUpdate;
    private IsActivated isActivated;
}
