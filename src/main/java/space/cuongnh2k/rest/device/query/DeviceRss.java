package space.cuongnh2k.rest.device.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import space.cuongnh2k.core.enums.IsActivated;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class DeviceRss {
    private String id;
    private String userAgent;
    private String activationCode;
    private IsActivated isActivated;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
