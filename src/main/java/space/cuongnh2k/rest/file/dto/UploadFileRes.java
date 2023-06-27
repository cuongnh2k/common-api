package space.cuongnh2k.rest.file.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessEnum;

@Getter
@Setter
public class UploadFileRes {
    private String id;
    private String name;
    private String contentType;
    private Long size;
    private String url;
    private AccessEnum access;
}
