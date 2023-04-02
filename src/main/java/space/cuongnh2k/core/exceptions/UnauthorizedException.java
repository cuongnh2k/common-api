package space.cuongnh2k.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException {

    private List<String> errors;
}
