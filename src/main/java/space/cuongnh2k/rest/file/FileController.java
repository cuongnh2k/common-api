package space.cuongnh2k.rest.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.annotation.UUID;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.core.enums.AccessEnum;

import java.util.List;

@Validated
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Privileges("")
    @PostMapping
    public ResponseEntity<BaseResponseDto> uploadFile(@RequestParam(defaultValue = "PRIVATE") AccessEnum access,
                                                      @RequestParam List<MultipartFile> files) {
        return BaseResponseDto.success(fileService.uploadFile(access, files));
    }

    @Privileges("")
    @PostMapping("/delete")
    public ResponseEntity<BaseResponseDto> deleteFile(@RequestBody @UUID List<String> ids) {
        fileService.deleteFile(ids);
        return BaseResponseDto.success("Xóa file thành công");
    }

    @Privileges("OPTIONAL")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable @UUID String id,
                                          @RequestParam(defaultValue = "true") Boolean responseBase64) {
        return fileService.downloadFile(id, responseBase64);
    }
}
