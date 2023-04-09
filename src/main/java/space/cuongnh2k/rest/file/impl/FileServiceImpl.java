package space.cuongnh2k.rest.file.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.*;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.file.FileRepository;
import space.cuongnh2k.rest.file.FileService;
import space.cuongnh2k.rest.file.dto.FileRes;
import space.cuongnh2k.rest.file.query.CreateFilePrt;

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

    @Value("${application.bucket-name.public}")
    private String BUCKET_NAME_PUBLIC;

    @Value("${application.bucket-name.private}")
    private String BUCKET_NAME_PRIVATE;

    @Value("${cloud.aws.region.static}")
    private String BUCKET_REGION;

    @Override
    public List<FileRes> uploadFile(AccessTypeEnum access, FileTypeEnum type, List<MultipartFile> files) {
        List<CreateFilePrt> listPrt = new ArrayList<>();
        //valid
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (fileExtension.equals("")) {
                throw new BusinessLogicException();
            }
            switch (type) {
                case IMAGE:
                    if (!fileExtension.equalsIgnoreCase(ImageFormatEnum.apng.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.gif.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.ico.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.cur.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.jpg.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.jpeg.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.jfif.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.pjpeg.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.pjp.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.png.toString())
                            && !fileExtension.equalsIgnoreCase(ImageFormatEnum.svg.toString())) {
                        throw new BusinessLogicException();
                    }
                    break;
                case AUDIO:
                    if (!fileExtension.equalsIgnoreCase(AudioFormatEnum.mp3.toString())
                            && !fileExtension.equalsIgnoreCase(AudioFormatEnum.wav.toString())
                            && !fileExtension.equalsIgnoreCase(AudioFormatEnum.ogg.toString())) {
                        throw new BusinessLogicException();
                    }
                    break;
                case VIDEO:
                    if (!fileExtension.equalsIgnoreCase(VideoFormatEnum.mp4.toString())
                            && !fileExtension.equalsIgnoreCase(VideoFormatEnum.webm.toString())
                            && !fileExtension.equalsIgnoreCase(VideoFormatEnum.ogg.toString())) {
                        throw new BusinessLogicException();
                    }
                    break;
                default:
                    break;
            }
            String id = UUID.randomUUID().toString();
            listPrt.add(CreateFilePrt.builder()
                    .id(id)
                    .accountId(authContext.getAccountId())
                    .url(access == AccessTypeEnum.PUBLIC ? "https://s3."
                            + BUCKET_REGION
                            + ".amazonaws.com/"
                            + BUCKET_NAME_PUBLIC
                            + "/"
                            + authContext.getAccountId()
                            + "/"
                            + id
                            + "."
                            + fileExtension.toLowerCase() : null)
                    .name(originalFilename)
                    .fileExtension(fileExtension.toLowerCase())
                    .access(access)
                    .type(type)
                    .file(file)
                    .build());
        }
        // insert
        if (fileRepository.uploadFile(listPrt) != listPrt.size()) {
            throw new BusinessLogicException();
        }
        listPrt.forEach(o -> {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(o.getFile().getSize());
            try {
                amazonS3.putObject(access == AccessTypeEnum.PUBLIC ? BUCKET_NAME_PUBLIC : BUCKET_NAME_PRIVATE,
                        authContext.getAccountId() + "/" + o.getId() + "." + o.getFileExtension(),
                        o.getFile().getInputStream(),
                        metadata);
            } catch (IOException e) {
                throw new BusinessLogicException();
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
}
