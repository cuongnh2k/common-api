package space.cuongnh2k.rest.device.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeleteDevicePrt {
    private String accountId;
    private List<String> ids;
    private String userAgent;
}
