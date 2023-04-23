package space.cuongnh2k.rest.account.query;

import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

@Getter
@Setter
public class AccountRss {
    private String id;
    private String email;
    private String password;
    private String activationCode;
    private IsActivated isActivated;
    private String firstName;
    private String lastName;
    private String avatarFileId;
}
