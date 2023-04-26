package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.AccessEnum;

@Getter
@Setter
@Builder
public class FileRss {
    private String id;
    private String ownerId;
    private String name;
    private String contentType;
    private Long size;
    private AccessEnum access;
}
