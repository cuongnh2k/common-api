package space.cuongnh2k.core.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class BaseAdvice {
    protected static final String MESSAGE_CODE_ERROR_MISMATCH = "space.cuongnh2k.constraints.Mismatch.message";
    @Autowired
    protected MessageSource messageSource;
}
