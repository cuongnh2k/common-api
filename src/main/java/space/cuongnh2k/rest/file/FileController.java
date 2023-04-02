package space.cuongnh2k.rest.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import space.cuongnh2k.core.enums.AccessTypeEnum;

@Validated
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam(defaultValue = "PRIVATE") AccessTypeEnum access,
                                         @RequestParam MultipartFile[] files) {
        return new ResponseEntity<>(fileService.uploadFile(files), HttpStatus.OK);
    }
}
