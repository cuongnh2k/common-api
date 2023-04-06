package space.cuongnh2k.rest.device.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenRes {
    private String accessToken;
    private String refreshToken;
}
