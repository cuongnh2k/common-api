package space.cuongnh2k.rest.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import space.cuongnh2k.core.base.BaseProduceDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRes extends BaseProduceDto<String> {
    private String userAgent;
}
