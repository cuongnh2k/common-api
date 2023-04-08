package space.cuongnh2k.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import space.cuongnh2k.core.validations.IDCollectionValidator;
import space.cuongnh2k.core.validations.IDValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Repeatable(ID.List.class)
@Constraint(validatedBy = {IDValidator.class, IDCollectionValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface ID {
    String message() default "{space.cuongnh2k.constraints.ID.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long min() default 1L;

    long max() default Long.MAX_VALUE;

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ID[] value();
    }
}
