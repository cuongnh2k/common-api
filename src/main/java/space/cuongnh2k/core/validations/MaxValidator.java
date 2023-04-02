package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.Max;

public class MaxValidator implements ConstraintValidator<Max, Object> {

    private double max;

    @Override
    public void initialize(Max constraintAnnotation) {
        this.max = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer && (Integer) value > (int) max) {
            return false;
        }
        if (value instanceof Long && (Long) value > (long) max) {
            return false;
        }
        if (value instanceof Float && (Float) value > (float) max) {
            return false;
        }
        if (value instanceof Double && (Double) value > max) {
            return false;
        }
        if (!(value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double)) {
            throw new RuntimeException(String.format("Max validator is not supported for %s", value.getClass().getSimpleName()));
        }
        return true;
    }
}
