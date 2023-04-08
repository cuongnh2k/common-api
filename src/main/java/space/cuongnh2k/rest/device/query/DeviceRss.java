package space.cuongnh2k.rest.device.query;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
public class DeviceRss {
    private String id;
    private String userAgent;
    private IsActivated isActivated;

}
