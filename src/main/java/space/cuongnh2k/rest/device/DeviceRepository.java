package space.cuongnh2k.rest.device;

import space.cuongnh2k.rest.device.query.CreateDevicePrt;
import space.cuongnh2k.rest.device.query.DeviceRss;
import space.cuongnh2k.rest.device.query.GetDevicePrt;
import space.cuongnh2k.rest.device.query.UpdateDevicePrt;

import java.util.List;

public interface DeviceRepository {
    List<DeviceRss> getDevice(GetDevicePrt prt);

    int createDevice(CreateDevicePrt prt);

    int updateDevice(UpdateDevicePrt prt);
}
