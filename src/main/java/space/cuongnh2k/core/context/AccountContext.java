package space.cuongnh2k.core.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountContext {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarFileId;
    private String avatarUrl;
}
