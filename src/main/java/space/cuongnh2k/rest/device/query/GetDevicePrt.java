package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetDevicePrt {
    private String id;
    private String accountId;
    private String userAgent;
}
