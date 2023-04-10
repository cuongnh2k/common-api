package space.cuongnh2k.rest.device;

import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.dto.DeviceRes;

import java.util.List;

public interface DeviceService {
    void activeDevice(ActiveDeviceReq req);

    void logout(Boolean isLogoutAll,List<String> ids);

    List<DeviceRes> getListDevice();
}
