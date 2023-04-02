package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.Pattern;

public class PatternValidator implements ConstraintValidator<Pattern, String> {

    private String regex;

    @Override
    public void initialize(Pattern constraintAnnotation) {
        this.regex = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(this.regex);
    }
}
