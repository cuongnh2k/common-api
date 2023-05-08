package space.cuongnh2k.rest.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRes {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarFileId;
}
