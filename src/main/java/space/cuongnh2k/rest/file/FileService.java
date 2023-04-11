package space.cuongnh2k.rest.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.rest.file.dto.FileRes;

import java.util.List;

public interface FileService {
    List<FileRes> uploadFile(AccessTypeEnum access, List<MultipartFile> files);

    void deleteFile(Boolean isDeleteAll, List<String> ids);

    ResponseEntity<byte[]> downloadFile(String id);
}
