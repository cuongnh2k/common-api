package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.core.enums.IsDeleted;

import java.util.List;

@Getter
@Setter
@Builder
public class FileRss {
    private String id;
    private String createdBy;
    private String url;
    private String name;
    private String contentType;
    private Long size;
    private AccessTypeEnum access;
}
