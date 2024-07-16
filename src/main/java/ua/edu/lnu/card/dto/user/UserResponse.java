package ua.edu.lnu.card.dto.user;

import ua.edu.lnu.card.entity.Role;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
public record UserResponse(Long id, String firstName, String lastName, String email, LocalDate birthdate,
                           Instant createdAt, Role role) implements Serializable {
}