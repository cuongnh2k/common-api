package space.cuongnh2k.rest.account.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountActivationPrt {
    private String code;
    private String createdTime;
}
