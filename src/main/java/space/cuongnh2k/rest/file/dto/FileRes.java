package space.cuongnh2k.rest.file.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.core.enums.FileTypeEnum;

@Getter
@Setter
public class FileRes {
    private String id;
    private String accountId;
    private String url;
    private String name;
    private AccessTypeEnum access;
    private FileTypeEnum type;
}
