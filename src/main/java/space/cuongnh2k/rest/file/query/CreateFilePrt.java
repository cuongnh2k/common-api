package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.enums.AccessEnum;

@Getter
@Setter
@Builder
public class CreateFilePrt {
    private String id;
    private String ownerId;
    private String name;
    private String contentType;
    private Long size;
    private AccessEnum access;
    private String fileExtension;
    private MultipartFile file;
}
