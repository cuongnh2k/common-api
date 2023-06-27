package space.cuongnh2k.rest.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessEnum;

@Getter
@Setter
@Builder
public class DownloadFileRes {
    private String id;
    private String name;
    private String contentType;
    private Long size;
    private AccessEnum access;
    private String url;
    private String file;
}
