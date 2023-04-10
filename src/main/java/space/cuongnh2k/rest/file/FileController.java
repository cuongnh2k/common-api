package space.cuongnh2k.rest.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.enums.AccessTypeEnum;

import java.util.List;

@Validated
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Privileges
    @PostMapping
    public ResponseEntity<BaseResponseDto> upload(@RequestParam(defaultValue = "PRIVATE") AccessTypeEnum access,
                                                  @RequestParam List<MultipartFile> files) {
        return BaseResponseDto.success("Upload file successful", fileService.uploadFile(access, files));
    }
}
