package space.cuongnh2k.rest.index;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.cuongnh2k.core.base.BaseResponseDto;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ResponseEntity<BaseResponseDto> index() {
        return BaseResponseDto.success(ZonedDateTime.now());
    }
}
