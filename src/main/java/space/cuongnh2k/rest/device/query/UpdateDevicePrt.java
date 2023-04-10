package space.cuongnh2k.rest.device.query;

import lombok.*;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.enums.IsDeleted;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDevicePrt {
    private String id;
    private String accountId;
    private List<String> ids;
    private String accessToken;
    private String refreshToken;
    private String activationCodeUseActive;
    private String activationCodeUseUpdate;
    private IsActivated isActivated;
    private IsDeleted isDeleted;
}
