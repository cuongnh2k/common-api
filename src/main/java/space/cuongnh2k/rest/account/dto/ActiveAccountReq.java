package space.cuongnh2k.rest.account.dto;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.UUID;

@Getter
@Setter
public class ActiveAccountReq {
    @UUID
    private String id;

    @UUID
    private String activationCode;
}
