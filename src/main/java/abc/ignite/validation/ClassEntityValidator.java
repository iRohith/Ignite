package abc.ignite.validation;

import abc.ignite.dto.ClassRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClassEntityValidator implements ConstraintValidator<ClassEntityConstraints, ClassRequestDTO> {
    @Override
    public boolean isValid(ClassRequestDTO classSession, ConstraintValidatorContext context) {
        // Check if end date is after start date
        boolean isEndDateValid = classSession.getEndDate().isAfter(classSession.getStartDate());

        // Check if start time + duration(minutes) doesn't overflow into next day
        boolean isStartTimeValid = classSession.getStartTime().plusMinutes(classSession.getDuration()).isAfter(classSession.getStartTime());

        if (!(isEndDateValid && isStartTimeValid)) {
            context.disableDefaultConstraintViolation();
        
            if (!isEndDateValid){
                context
                    .buildConstraintViolationWithTemplate("endDate must be in future after startDate")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            }

            if (!isStartTimeValid){
                context
                    .buildConstraintViolationWithTemplate("'startTime' + 'duration' should not overflow into next day.")
                    .addPropertyNode("startTime")
                    .addConstraintViolation();
            }
        }


        return isEndDateValid && isStartTimeValid;
    }
}