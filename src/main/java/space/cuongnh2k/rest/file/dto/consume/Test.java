package space.cuongnh2k.rest.file.dto.consume;

import lombok.Data;
import space.cuongnh2k.core.annotation.MaxLength;
import space.cuongnh2k.core.annotation.Required;
import space.cuongnh2k.core.annotation.UUID;

@Data
public class Test {
    @UUID
    private String id;

    @Required
    @MaxLength(3)
    private String name;
}
