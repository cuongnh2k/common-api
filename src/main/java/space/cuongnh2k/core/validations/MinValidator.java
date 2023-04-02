package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.Min;

public class MinValidator implements ConstraintValidator<Min, Object> {

    private double min;

    @Override
    public void initialize(Min constraintAnnotation) {
        this.min = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer && (Integer) value < (int) min) {
            return false;
        }
        if (value instanceof Long && (Long) value < (long) min) {
            return false;
        }
        if (value instanceof Float && (Float) value < (float) min) {
            return false;
        }
        if (value instanceof Double && (Double) value < min) {
            return false;
        }
        if (!(value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double)) {
            throw new RuntimeException(String.format("Max validator is not supported for %s", value.getClass().getSimpleName()));
        }
        return true;
    }
}
