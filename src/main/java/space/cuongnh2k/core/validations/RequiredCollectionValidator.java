package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.annotation.Required;

import java.util.Collection;

public class RequiredCollectionValidator implements ConstraintValidator<Required, Collection<?>> {

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        return !CollectionUtils.isEmpty(value);
    }
}
