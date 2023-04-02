package space.cuongnh2k.rest.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile[] file);
}
