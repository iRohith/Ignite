package abc.ignite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClassEntityValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassEntityConstraints {
    String message() default "'endDate' must be in future after startDate. 'startTime' + 'duration' should not overflow into next day.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}