package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.MinLength;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {

    private long min;

    @Override
    public void initialize(MinLength constraintAnnotation) {
        this.min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.length() >= this.min;
    }
}
