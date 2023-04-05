package space.cuongnh2k.rest.account.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetAccountPrt {
    private String email;
    private String id;
}
