package ua.edu.lnu.card.dto.user;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Value
public class AdminCreationUpdateRequest implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDate birthdate;
    Long roleId;
}