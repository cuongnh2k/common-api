package space.cuongnh2k.rest.account.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAccountRes {
    private String id;
    private String firstName;
    private String lastName;
    private String avatarFileId;
}
