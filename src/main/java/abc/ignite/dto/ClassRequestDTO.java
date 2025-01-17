package abc.ignite.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import abc.ignite.validation.ClassIdExist;
import abc.ignite.validation.ClassEntityConstraints;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ClassEntityConstraints
public class ClassRequestDTO {
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{2,19}$", message = "'classId' must start with a letter and can contain letters, numbers, underscores, and hyphens. Length must be between 3 and 20 characters.")
    @ClassIdExist(exist = false, message = "'classId' already exists.")
    @Nonnull
    private String classId;

    @Pattern(regexp = "^[A-Za-z]+([ '-][A-Za-z]+)*$", message = "'name' must contain only letters, spaces, hyphens, and apostrophes.")
    @Nonnull
    private String name;

    @Future(message = "'startDate' should be in the future.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Nonnull
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Nonnull
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Nonnull
    private LocalTime startTime;

    @Min(value = 10, message = "Minimum duration 10 minutes.")
    @Max(value = 10 * 60, message = "Maximum duration 10 hours or 600 minutes.")
    private Integer duration; // Minutes

    @Min(value = 1, message = "'capacity' must be at least 1.")
    private Integer capacity;
}
