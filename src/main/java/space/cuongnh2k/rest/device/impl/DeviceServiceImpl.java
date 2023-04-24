package space.cuongnh2k.rest.device.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.DeviceService;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.dto.DeviceRes;
import space.cuongnh2k.rest.device.query.DeleteDevicePrt;
import space.cuongnh2k.rest.device.query.GetDevicePrt;
import space.cuongnh2k.rest.device.query.UpdateDevicePrt;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final AuthContext authContext;

    @Override
    public void activeDevice(ActiveDeviceReq req) {
        UpdateDevicePrt prt = new UpdateDevicePrt();
        BeanCopyUtil.copyProperties(prt, req);
        prt.setIsActivated(IsActivated.YES);
        prt.setActivationCode(req.getActivationCode());
        if (deviceRepository.updateDevice(prt) != 1) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0008);
        }
    }

    @Override
    public void logout(Boolean isLogoutAll, List<String> ids) {
        if (isLogoutAll) {
            if (deviceRepository.deleteDevice(DeleteDevicePrt.builder()
                    .accountId(authContext.getAccountId())
                    .build()) == 0) {
                throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0010);
            }
            return;
        }
        if (deviceRepository.deleteDevice(DeleteDevicePrt.builder()
                .ids(ids)
                .accountId(authContext.getAccountId())
                .build()) != ids.size()) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0010);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceRes> getListDevice() {
        return deviceRepository.getDevice(GetDevicePrt.builder()
                        .accountId(authContext.getAccountId())
                        .isActivated(IsActivated.YES)
                        .build())
                .stream()
                .map(o -> {
                    DeviceRes res = new DeviceRes();
                    BeanCopyUtil.copyProperties(res, o);
                    return res;
                })
                .collect(Collectors.toList());
    }
}
