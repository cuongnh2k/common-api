package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.annotation.ID;

import java.util.List;

public class IDCollectionValidator implements ConstraintValidator<ID, List<Long>> {
    private long min;
    private long max;

    @Override
    public void initialize(ID constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return false;
        }
        for (Long o : value) {
            if (o < this.min || o > this.max) {
                return false;
            }
        }
        return true;
    }
}
