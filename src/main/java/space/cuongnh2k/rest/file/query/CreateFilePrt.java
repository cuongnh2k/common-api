package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.core.enums.FileTypeEnum;

@Getter
@Setter
@Builder
public class CreateFilePrt {
    private String id;
    private String accountId;
    private String url;
    private String name;
    private String fileExtension;
    private AccessTypeEnum access;
    private FileTypeEnum type;
    private MultipartFile file;
}
