package abc.ignite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClassIdExistValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassIdExist {
    String message() default "'classId' not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean exist() default true;
}