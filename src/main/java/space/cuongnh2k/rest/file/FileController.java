package space.cuongnh2k.rest.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.annotation.UUID;
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
    public ResponseEntity<BaseResponseDto> uploadFile(@RequestParam(defaultValue = "PRIVATE") AccessTypeEnum access,
                                                      @RequestParam List<MultipartFile> files) {
        return BaseResponseDto.success("Upload file successful", fileService.uploadFile(access, files));
    }

    @Privileges
    @PostMapping("/delete")
    public ResponseEntity<BaseResponseDto> deleteFile(@RequestParam(defaultValue = "false") Boolean isDeleteAll,
                                                      @RequestBody @UUID List<String> ids) {
        fileService.deleteFile(isDeleteAll, ids);
        return BaseResponseDto.success("Deleted file successful");
    }

    @Privileges
    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable @UUID String id) {
        return fileService.downloadFile(id);
    }
}
