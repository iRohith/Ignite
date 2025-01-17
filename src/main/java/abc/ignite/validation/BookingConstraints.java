package abc.ignite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookingValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingConstraints {
    String message() default "Invalid values";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}