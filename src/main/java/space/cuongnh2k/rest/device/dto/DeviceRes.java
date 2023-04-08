package space.cuongnh2k.rest.device.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
public class DeviceRes {
    private String id;
    private String userAgent;
}
