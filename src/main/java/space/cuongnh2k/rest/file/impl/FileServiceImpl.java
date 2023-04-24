package space.cuongnh2k.rest.file.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.FileService;
import space.cuongnh2k.rest.file.dto.FileRes;
import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.DeleteFilePrt;
import space.cuongnh2k.rest.file.query.FileRss;
import space.cuongnh2k.rest.file.query.GetFilePrt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final AmazonS3 amazonS3;
    private final AuthContext authContext;
    private final FileRepository fileRepository;

    @Value("${application.bucket-name}")
    private String BUCKET_NAME_PRIVATE;

    @Value("${application.max-file-size}")
    private Integer MAX_FILE_SIZE;

    @Override
    public List<FileRes> uploadFile(List<MultipartFile> files) {
        List<CreateFilePrt> listPrt = new ArrayList<>();
        //valid
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (fileExtension.equals("")) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0012);
            }
            String id = UUID.randomUUID().toString();
            listPrt.add(CreateFilePrt.builder()
                    .id(id)
                    .ownerId(authContext.getAccountId())
                    .name(originalFilename.length() > 255
                            ? originalFilename.toLowerCase().substring(originalFilename.length() - 255)
                            : originalFilename.toLowerCase())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .fileExtension(fileExtension.toLowerCase())
                    .file(file)
                    .build());
        }
        // insert
        if (fileRepository.uploadFile(listPrt) != listPrt.size()) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0013);
        }
        listPrt.forEach(o -> {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(o.getFile().getSize());
            metadata.setContentType(o.getContentType());
            try {
                amazonS3.putObject(BUCKET_NAME_PRIVATE,
                        authContext.getAccountId() + "/" + o.getId() + "." + o.getFileExtension(),
                        o.getFile().getInputStream(),
                        metadata);
            } catch (IOException e) {
                log.error(e);
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0013);
            }
        });
        return listPrt.stream()
                .map(o -> {
                    FileRes res = new FileRes();
                    BeanCopyUtil.copyProperties(res, o);
                    return res;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(List<String> ids) {
        List<FileRss> listFileRss;
        listFileRss = fileRepository.getFile(GetFilePrt.builder()
                .ownerId(authContext.getAccountId())
                .ids(ids).build());
        if (fileRepository.deleteFile(DeleteFilePrt.builder()
                .ids(ids)
                .ownerId(authContext.getAccountId())
                .build()) != ids.size()) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0014);
        }
        try {
            listFileRss.forEach(o ->
                    amazonS3.deleteObject(BUCKET_NAME_PRIVATE,
                            authContext.getAccountId() + "/" + o.getId() + o.getName().substring(o.getName().lastIndexOf(".")))

            );
        } catch (Exception e) {
            log.error(e);
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0014);
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String id) {
        List<FileRss> listFileRss = fileRepository.getFile(GetFilePrt.builder()
                .id(id)
                .build());
        if (CollectionUtils.isEmpty(listFileRss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0015);
        }
        try {
            S3Object s3object = amazonS3.getObject(
                    new GetObjectRequest(BUCKET_NAME_PRIVATE,
                            listFileRss.get(0).getOwnerId() + "/" + listFileRss.get(0).getId() + listFileRss.get(0).getName().substring(listFileRss.get(0).getName().lastIndexOf("."))));
            return ResponseEntity.ok()
                    .header("Content-Type", listFileRss.get(0).getContentType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + listFileRss.get(0).getName() + "\"")
                    .body(new InputStreamResource(s3object.getObjectContent()).getContentAsByteArray());
        } catch (Exception e) {
            log.error(e);
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0016);
        }
    }
}
