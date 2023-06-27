package space.cuongnh2k.rest.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.Length;
import space.cuongnh2k.core.annotation.MaxLength;
import space.cuongnh2k.core.annotation.Password;

@Getter
@Setter
@Builder
public class UpdateAccountReq {
    @Password
    private String password;

    @Password
    private String passwordOld;

    @MaxLength(value = 50)
    private String firstName;

    @MaxLength(value = 50)
    private String lastName;

    @Length(36)
    private String avatarFileId;

    private String avatarUrl;
}
