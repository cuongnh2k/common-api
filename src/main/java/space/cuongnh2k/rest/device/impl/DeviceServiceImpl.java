package space.cuongnh2k.rest.device.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.core.utils.BeanCopyUtil;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.DeviceService;
import space.cuongnh2k.rest.device.dto.ActiveDeviceReq;
import space.cuongnh2k.rest.device.dto.DeviceRes;
import space.cuongnh2k.rest.device.query.*;

import java.time.LocalDateTime;
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
        List<DeviceRss> listDeviceRss = deviceRepository.getDevice(GetDevicePrt.builder()
                .id(req.getId())
                .build());
        if (CollectionUtils.isEmpty(listDeviceRss)) {
            throw new BusinessLogicException();
        }
        ActivationCodePrt activationCodePrt = new Gson().fromJson(listDeviceRss.get(0).getActivationCode(), ActivationCodePrt.class);
        if (LocalDateTime.parse(activationCodePrt.getDevice().getCreatedDate())
                .plusMinutes(5).compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessLogicException();
        }
        if (!activationCodePrt.getDevice().getCode().equals(req.getActivationCode())) {
            throw new BusinessLogicException();
        }

        if (deviceRepository.updateDevice(UpdateDevicePrt.builder()
                .id(listDeviceRss.get(0).getId())
                .isActivated(IsActivated.YES)
                .build()) != 1) {
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
