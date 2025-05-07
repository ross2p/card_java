package ua.edu.lnu.card.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Value
@Builder
public class UserCreationUpdateRequest implements Serializable {

    @NotBlank(message = "First name is required")
    String firstName;

    @NotBlank(message = "Last name is required")
    String lastName;

    @Email
    String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character."
    )
    String password;

    LocalDate birthdate;
}


