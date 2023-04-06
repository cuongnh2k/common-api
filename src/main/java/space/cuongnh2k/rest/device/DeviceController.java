package space.cuongnh2k.rest.device;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/active")
    public ResponseEntity<BaseResponseDto> activeAccount(@RequestBody @Valid ActiveDeviceReq req) {
        deviceService.activeDevice(req);
        return BaseResponseDto.success("Active account successful");
    }
}
