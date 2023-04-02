package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.UUID;

import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<UUID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && Pattern.matches("^[a-z\\d]{8}\\-[a-z\\d]{4}\\-[a-z\\d]{4}\\-[a-z\\d]{4}\\-[a-z\\d]{12}$", value);
    }
}
