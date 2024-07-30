package ua.edu.lnu.card.dto.user;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Value
@Builder

public class UserCreationUpdateRequest implements Serializable {
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDate birthdate;
}