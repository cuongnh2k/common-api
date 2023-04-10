package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.cuongnh2k.core.enums.IsDeleted;

import java.util.List;

@Getter
@Setter
@Builder
public class UpdateFilePrt {
    private List<String> ids;
    private String accountId;
    private IsDeleted isDeleted;
}
