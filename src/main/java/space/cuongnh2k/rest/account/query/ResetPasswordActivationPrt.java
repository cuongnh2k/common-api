package space.cuongnh2k.rest.account.query;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetPasswordActivationPrt {
    private String code;
    private String createdDate;
}
