package space.cuongnh2k.rest.file.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetFilePrt {
    private String id;
    private List<String> ids;
    private String ownerId;
}
