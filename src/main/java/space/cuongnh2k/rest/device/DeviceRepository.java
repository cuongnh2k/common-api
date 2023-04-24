package space.cuongnh2k.rest.device;

import space.cuongnh2k.rest.device.query.*;

import java.util.List;

public interface DeviceRepository {
    List<DeviceRss> getDevice(GetDevicePrt prt);

    int createDevice(CreateDevicePrt prt);

    int updateDevice(UpdateDevicePrt prt);

    int deleteDevice(DeleteDevicePrt prt);
}
