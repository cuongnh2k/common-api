package space.cuongnh2k.rest.device;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.cuongnh2k.core.annotation.Privileges;
import space.cuongnh2k.core.base.BaseResponseDto;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    @PatchMapping("/active")
    public ResponseEntity<BaseResponseDto> activeDevice(@RequestBody @Valid ActiveDeviceReq req) {
        deviceService.activeDevice(req);
        return BaseResponseDto.success("Kích hoạt thiết bị thành công");
    }

    @Privileges("")
    @PostMapping
    public ResponseEntity<BaseResponseDto> logout(@RequestParam(defaultValue = "false") Boolean isLogoutAll,
                                                  @RequestBody List<String> ids) {
        deviceService.logout(isLogoutAll, ids);
        return BaseResponseDto.success("Đăng xuất thành công");
    }

    @Privileges("")
    @GetMapping
    public ResponseEntity<BaseResponseDto> getListDevice() {
        return BaseResponseDto.success(deviceService.getListDevice());
    }
}
