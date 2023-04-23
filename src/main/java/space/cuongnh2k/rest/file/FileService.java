package space.cuongnh2k.rest.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.rest.file.dto.FileRes;

import java.util.List;

public interface FileService {
    List<FileRes> uploadFile(List<MultipartFile> files);

    void deleteFile(List<String> ids);

    ResponseEntity<byte[]> downloadFile(String id);
}
