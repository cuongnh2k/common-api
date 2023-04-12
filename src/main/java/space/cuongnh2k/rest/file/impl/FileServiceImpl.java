package space.cuongnh2k.rest.file.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsDeleted;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.FileService;
import space.cuongnh2k.rest.file.dto.FileRes;
import space.cuongnh2k.rest.file.query.CreateFilePrt;
import space.cuongnh2k.rest.file.query.FileRss;
import space.cuongnh2k.rest.file.query.GetFilePrt;
import space.cuongnh2k.rest.file.query.UpdateFilePrt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Value("${application.bucket-name.public}")
    private String BUCKET_NAME_PUBLIC;

    @Value("${application.bucket-name.private}")
    private String BUCKET_NAME_PRIVATE;

    @Value("${cloud.aws.region.static}")
    private String BUCKET_REGION;

    @Value("${application.max-file-size}")
    private Integer MAX_FILE_SIZE;

    @Override
    public List<FileRes> uploadFile(AccessTypeEnum access, List<MultipartFile> files) {
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
                    .accountId(authContext.getAccountId())
                    .url(access == AccessTypeEnum.PUBLIC ? "https://s3." + BUCKET_REGION + ".amazonaws.com/" + BUCKET_NAME_PUBLIC + "/" + authContext.getAccountId() + "/" + id + "." + fileExtension.toLowerCase() : null)
                    .name(originalFilename.toLowerCase())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .access(access)
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
                amazonS3.putObject(access == AccessTypeEnum.PUBLIC ? BUCKET_NAME_PUBLIC : BUCKET_NAME_PRIVATE,
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
    public void deleteFile(Boolean isDeleteAll, List<String> ids) {
        List<FileRss> listFileRss;
        if (isDeleteAll) {
            listFileRss = fileRepository.getFile(GetFilePrt.builder().accountId(authContext.getAccountId()).build());
            if (fileRepository.updateFile(UpdateFilePrt.builder()
                    .accountId(authContext.getAccountId())
                    .isDeleted(IsDeleted.YES)
                    .build()) == 0) {
                throw new BusinessLogicException();
            }
        } else {
            listFileRss = fileRepository.getFile(GetFilePrt.builder().accountId(authContext.getAccountId()).ids(ids).build());
            if (fileRepository.updateFile(UpdateFilePrt.builder()
                    .ids(ids)
                    .accountId(authContext.getAccountId())
                    .isDeleted(IsDeleted.YES)
                    .build()) != ids.size()) {
                throw new BusinessLogicException();
            }
        }
        try {
            listFileRss.forEach(o ->
                    amazonS3.deleteObject(o.getAccess() == AccessTypeEnum.PUBLIC ? BUCKET_NAME_PUBLIC : BUCKET_NAME_PRIVATE,
                            authContext.getAccountId() + "/" + o.getId() + o.getName().substring(o.getName().lastIndexOf(".")))

            );
        } catch (Exception e) {
            log.error(e);
            throw new BusinessLogicException();
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String id) {
        List<FileRss> listFileRss = fileRepository.getFile(GetFilePrt.builder()
                .id(id)
                .build());
        if (CollectionUtils.isEmpty(listFileRss)) {
            throw new BusinessLogicException();
        }
        try {
            S3Object s3object = amazonS3.getObject(
                    new GetObjectRequest(
                            listFileRss.get(0).getAccess() == AccessTypeEnum.PUBLIC ? BUCKET_NAME_PUBLIC : BUCKET_NAME_PRIVATE,
                            listFileRss.get(0).getAccountId() + "/" + listFileRss.get(0).getId() + listFileRss.get(0).getName().substring(listFileRss.get(0).getName().lastIndexOf("."))));
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[MAX_FILE_SIZE * 1024 * 1024];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return ResponseEntity.ok()
                    .header("Content-Type", listFileRss.get(0).getContentType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + listFileRss.get(0).getName() + "\"")
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            log.error(e);
            throw new BusinessLogicException();
        }
    }
}
