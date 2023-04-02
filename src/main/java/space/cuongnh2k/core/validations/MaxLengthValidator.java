package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.MaxLength;

public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {

    private long max;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        this.max = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.length() <= this.max;
    }
}
