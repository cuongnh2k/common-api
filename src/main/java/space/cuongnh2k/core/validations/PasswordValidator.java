package space.cuongnh2k.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import space.cuongnh2k.core.annotation.Email;
import space.cuongnh2k.core.annotation.Password;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W\\_])[A-Za-z\\d\\W\\_]{8,10}$", value);
    }
}
