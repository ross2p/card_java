package ua.edu.lnu.card.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationUpdateRequest implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDate birthdate;
}