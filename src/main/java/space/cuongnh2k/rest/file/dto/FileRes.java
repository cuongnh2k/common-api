package space.cuongnh2k.rest.file.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessTypeEnum;

@Getter
@Setter
public class FileRes {
    private String id;
    private String accountId;
    private String url;
    private String name;
    private String contentType;
    private Long size;
    private AccessTypeEnum access;
}
