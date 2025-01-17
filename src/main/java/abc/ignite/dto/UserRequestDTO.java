package abc.ignite.dto;

import abc.ignite.validation.UsernameExist;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequestDTO {

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{2,19}$", message = "'username' must start with a letter and can contain letters, numbers, underscores, and hyphens. Length must be between 3 and 20 characters.")
    @UsernameExist(exist = false, message = "'username' already exists.")
    @Nonnull
    private String username;

    @Pattern(regexp = "^[A-Za-z]+([ '-][A-Za-z]+)*$", message = "'fullName' must contain only letters, spaces, hyphens, and apostrophes.")
    private String fullName;

    @Pattern(regexp = "(?i)admin|user", message = "'role' should be either admin or user.")
    @Nonnull
    private String role;
}
