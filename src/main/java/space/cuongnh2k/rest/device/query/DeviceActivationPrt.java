package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceActivationPrt {
    private String code;
    private String createdTime;
}
