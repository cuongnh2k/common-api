package space.cuongnh2k.rest.account.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.annotation.MaxLength;
import space.cuongnh2k.core.annotation.Password;
import space.cuongnh2k.core.annotation.Required;
import space.cuongnh2k.core.annotation.UUID;

@Getter
@Setter
@Builder
public class CreateAccountReq {

    @Email
    @Required
    private String email;

    @Required
    @Password
    private String password;

    @Required
    @MaxLength(value = 50)
    private String firstName;

    @MaxLength(value = 50)
    private String lastName;
}
