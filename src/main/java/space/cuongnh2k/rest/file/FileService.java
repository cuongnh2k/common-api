package space.cuongnh2k.rest.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.enums.AccessEnum;
import space.cuongnh2k.rest.file.dto.UploadFileRes;

import java.util.List;

public interface FileService {
    List<UploadFileRes> uploadFile(AccessEnum access, List<MultipartFile> files);

    void deleteFile(List<String> ids);

    ResponseEntity<?> downloadFile(String id, Boolean responseBase64);
}
