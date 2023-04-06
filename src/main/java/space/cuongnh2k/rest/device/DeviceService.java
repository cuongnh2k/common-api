package space.cuongnh2k.rest.device;

import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.dto.RefreshTokenRes;

public interface DeviceService {
    void activeDevice(ActiveDeviceReq req);

    RefreshTokenRes refreshToken();
}
