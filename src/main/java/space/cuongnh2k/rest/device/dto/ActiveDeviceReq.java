package space.cuongnh2k.rest.device.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.UUID;

@Getter
@Setter
public class ActiveDeviceReq {
    @UUID
    private String id;

    @UUID
    private String activationCode;
}
