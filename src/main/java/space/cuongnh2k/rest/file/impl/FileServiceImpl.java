package space.cuongnh2k.rest.file.impl;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.rest.file.FileService;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final AmazonS3 amazonS3;

    @Value("${application.bucket-name.public}")
    private String BUCKET_NAME_PUBLIC;

    @Value("${application.bucket-name.private}")
    private String BUCKET_NAME_PRIVATE;

    @Override
    public String uploadFile(MultipartFile[] files) {
//        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            amazonS3.putObject(BUCKET_NAME_PUBLIC, keyName + ".PNG", file.getInputStream(), metadata);
//            return "File uploaded: " + keyName;
//        } catch (Exception e) {
//            log.error(e);
//        }
        return "File not uploaded: " ;
    }

}
