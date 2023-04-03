package space.cuongnh2k.rest.account.query;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountPrt {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
