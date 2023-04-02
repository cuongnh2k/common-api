package space.cuongnh2k.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import space.cuongnh2k.core.validations.RequiredCollectionValidator;
import space.cuongnh2k.core.validations.RequiredObjectValidator;
import space.cuongnh2k.core.validations.RequiredStringValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Repeatable(Required.List.class)
@Constraint(validatedBy = {RequiredStringValidator.class, RequiredObjectValidator.class, RequiredCollectionValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface Required {

    String message() default "{space.cuongnh2k.constraints.Required.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @interface List {
        Required[] value();
    }
}
