package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.Length;

public class LengthValidator implements ConstraintValidator<Length, String> {

    private long length;

    @Override
    public void initialize(Length constraintAnnotation) {
        this.length = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.length() == this.length;
    }
}
