package abc.ignite.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import abc.ignite.validation.BookingConstraints;
import abc.ignite.validation.ClassIdExist;
import abc.ignite.validation.UsernameExist;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@BookingConstraints
public class BookingRequestDTO {
    @UsernameExist
    @Nonnull
    private String username;

    @ClassIdExist
    @Nonnull
    private String classId;

    @Future(message = "'date' (Participation date) must be in future.")
    @Nonnull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
