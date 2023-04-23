package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileRss {
    private String id;
    private String ownerId;
    private String name;
    private String contentType;
    private Long size;
}
