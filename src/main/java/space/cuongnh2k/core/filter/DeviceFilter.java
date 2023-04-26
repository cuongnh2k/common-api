package space.cuongnh2k.core.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.context.AuthContext;
import space.cuongnh2k.core.enums.IsActivated;
import space.cuongnh2k.rest.device.DeviceRepository;
import space.cuongnh2k.rest.device.query.DeviceRss;
import space.cuongnh2k.rest.device.query.GetDevicePrt;

import java.util.List;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
@RequiredArgsConstructor
public class DeviceFilter {
    private final DeviceRepository deviceRepository;
    private final HttpServletRequest request;
    private final AuthContext authContext;

    public String filter() {
        GetDevicePrt devicePrt;
        if (request.getRequestURI().contains("/auth/refresh-token")) {
            devicePrt = GetDevicePrt.builder()
                    .id(authContext.getDeviceId())
                    .accountId(authContext.getAccountId())
                    .refreshToken(authContext.getBearer())
                    .userAgent(request.getHeader(USER_AGENT))
                    .build();
        } else {
            devicePrt = GetDevicePrt.builder()
                    .id(authContext.getDeviceId())
                    .accountId(authContext.getAccountId())
                    .accessToken(authContext.getBearer())
                    .userAgent(request.getHeader(USER_AGENT))
                    .build();
        }

        List<DeviceRss> listDeviceRss = deviceRepository.getDevice(devicePrt);
        if (CollectionUtils.isEmpty(listDeviceRss)) {
            return "Device is logged out";
        } else if (listDeviceRss.get(0).getIsActivated() == IsActivated.NO) {
            return "Device is not activated";
        }
        return null;
    }
}
