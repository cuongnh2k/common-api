package space.cuongnh2k.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import space.cuongnh2k.core.validations.MinValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Repeatable(Min.List.class)
@Constraint(validatedBy = {MinValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface Min {
    String message() default "{space.cuongnh2k.constraints.Min.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double value() default Double.MAX_VALUE;

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @interface List {
        Min[] value();
    }
}
