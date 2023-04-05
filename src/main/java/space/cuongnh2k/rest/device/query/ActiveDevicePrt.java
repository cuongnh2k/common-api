package space.cuongnh2k.rest.device.query;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.UUID;

@Getter
@Setter
public class ActiveDevicePrt {
    @UUID
    private String id;

    @UUID
    private String activationCode;
}
