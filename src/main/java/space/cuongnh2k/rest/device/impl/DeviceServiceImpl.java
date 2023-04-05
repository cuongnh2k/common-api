package space.cuongnh2k.rest.device.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.DeviceService;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.query.UpdateDevicePrt;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public void activeDevice(ActiveDeviceReq req) {
        UpdateDevicePrt prt = new UpdateDevicePrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setIsActivated(IsActivated.YES);
        if (deviceRepository.updateDevice(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0008);
        }
    }
}
