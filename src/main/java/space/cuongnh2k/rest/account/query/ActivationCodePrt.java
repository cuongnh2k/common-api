package space.cuongnh2k.rest.account.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivationCodePrt {
    private AccountActivationPrt account;
    private ResetPasswordActivationPrt resetPassword;
}
