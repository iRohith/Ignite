package abc.ignite.validation;

import java.util.Optional;

import abc.ignite.dto.BookingRequestDTO;
import abc.ignite.entity.ClassEntity;
import abc.ignite.repository.BookingRepository;
import abc.ignite.repository.ClassRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingValidator implements ConstraintValidator<BookingConstraints, BookingRequestDTO> {

    private ClassRepository classRepository;
    private BookingRepository bookingRepository;

    @Override
    public boolean isValid(BookingRequestDTO bookingRequestDTO, ConstraintValidatorContext context) {
        Optional<ClassEntity> ocls = classRepository.findById(bookingRequestDTO.getClassId());

        if (ocls.isEmpty()){
            return false;
        }

        ClassEntity cls = ocls.get();
        boolean isCapacityValid = cls.getCapacity() > bookingRepository.countByClassEntityClassIdAndDate(bookingRequestDTO.getClassId(), bookingRequestDTO.getDate());

        boolean isAlreadyExisting = bookingRepository.existsByClassEntityClassIdAndUserUsernameAndDate(bookingRequestDTO.getClassId(), bookingRequestDTO.getUsername(), bookingRequestDTO.getDate());

        boolean isValidDate = cls.getDates().contains(bookingRequestDTO.getDate());

        boolean isValid = isCapacityValid && !isAlreadyExisting && isValidDate;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
        
            if (!isCapacityValid){
                context
                    .buildConstraintViolationWithTemplate("Class '%s' capcity (%d) reached.".formatted(cls.getName(), cls.getCapacity().intValue()))
                    .addPropertyNode("capacity")
                    .addConstraintViolation();
            }

            if (isAlreadyExisting){
                context
                    .buildConstraintViolationWithTemplate("User already booked the class '%s' on this date(%s).".formatted(cls.getName(), bookingRequestDTO.getDate()))
                    .addBeanNode()
                    .addConstraintViolation();
            }

            if (!isValidDate){
                context
                    .buildConstraintViolationWithTemplate("Class '%s' not available on this date(%s).".formatted(cls.getName(), bookingRequestDTO.getDate()))
                    .addPropertyNode("date")
                    .addConstraintViolation();
            }
        }

        return isValid;
    }
}