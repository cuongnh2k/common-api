package space.cuongnh2k.rest.file;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.enums.AccessTypeEnum;
import space.cuongnh2k.rest.file.dto.consume.Test;

@Validated
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam AccessTypeEnum access,
//                                         @RequestParam(required = false) MultipartFile[] files,
                                         @RequestBody @Valid Test test) {
//        return new ResponseEntity<>(fileService.uploadFile(files), HttpStatus.OK);
        return null;
    }
}
