package space.cuongnh2k.rest.account.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsActivated;

import java.util.List;

@Getter
@Setter
@Builder
public class GetAccountPrt {
    private List<String> emails;
    private List<String> ids;
    private IsActivated isActivated;
}
