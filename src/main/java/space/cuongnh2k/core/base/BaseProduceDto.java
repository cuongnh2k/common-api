package space.cuongnh2k.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseProduceDto<ID> {

    private ID id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updatedDate;
}
