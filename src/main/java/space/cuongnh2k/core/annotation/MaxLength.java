package space.cuongnh2k.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import space.cuongnh2k.core.validations.MaxLengthValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Repeatable(MaxLength.List.class)
@Constraint(validatedBy = {MaxLengthValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface MaxLength {
    String message() default "{space.cuongnh2k.constraints.MaxLength.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value() default Long.MAX_VALUE;

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @interface List {
        MaxLength[] value();
    }
}
