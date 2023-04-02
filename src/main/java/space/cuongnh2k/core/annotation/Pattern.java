package space.cuongnh2k.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import space.cuongnh2k.core.validations.PatternValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Repeatable(Pattern.List.class)
@Constraint(validatedBy = {PatternValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface Pattern {
    String message() default "{space.cuongnh2k.constraints.Pattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @interface List {
        Pattern[] value();
    }
}
