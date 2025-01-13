package ua.edu.lnu.card.dto.user;

import lombok.Value;
import ua.edu.lnu.card.dto.role.RoleResponse;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Value
public class UserResponse implements Serializable {
    UUID id;
    String firstName;
    String lastName;
    String email;
    LocalDate birthdate;
    RoleResponse role;
    Date createdAt;
    Date updatedAt;
}