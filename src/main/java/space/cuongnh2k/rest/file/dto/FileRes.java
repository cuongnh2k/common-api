package space.cuongnh2k.rest.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRes {
    private String id;
    private String name;
    private String contentType;
    private Long size;
}
