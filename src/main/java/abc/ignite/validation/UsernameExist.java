package abc.ignite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameExistValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameExist {
    String message() default "'username' not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean exist() default true;
}