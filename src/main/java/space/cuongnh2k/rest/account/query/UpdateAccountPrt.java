package space.cuongnh2k.rest.account.query;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
public class UpdateAccountPrt {
    private String id;
    private String activationCode;
    private IsActivated isActivated;
    private String password;
    private String firstName;
    private String lastName;
    private String avatarFileId;
}
