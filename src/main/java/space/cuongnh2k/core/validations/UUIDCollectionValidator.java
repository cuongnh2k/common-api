package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.annotation.UUID;

import java.util.List;
import java.util.regex.Pattern;

public class UUIDCollectionValidator implements ConstraintValidator<UUID, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(value)) {
            return false;
        }
        for (String o : value) {
            if (!Pattern.matches("^[a-z\\d]{8}\\-[a-z\\d]{4}\\-[a-z\\d]{4}\\-[a-z\\d]{4}\\-[a-z\\d]{12}$", o)) {
                return false;
            }
        }
        return true;
    }
}
