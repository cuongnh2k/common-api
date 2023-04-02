package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.ID;

public class IDValidator implements ConstraintValidator<ID, Long> {
    private long min;
    private long max;

    @Override
    public void initialize(ID constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && value >= this.min && value <= this.max;
    }
}
