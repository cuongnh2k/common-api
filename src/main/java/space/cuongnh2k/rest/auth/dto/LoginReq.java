package space.cuongnh2k.rest.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.Email;
import space.cuongnh2k.core.annotation.MaxLength;
import space.cuongnh2k.core.annotation.Password;
import space.cuongnh2k.core.annotation.Required;

@Getter
@Setter
@Builder
public class LoginReq {

    @Email
    @Required
    @MaxLength(value = 50)
    private String email;

    @Password
    @Required
    private String password;
}
