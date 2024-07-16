package ua.edu.lnu.card.dto.user;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
public record UserCreationUpdateRequest(String firstName, String lastName, String email, String password,
                                        LocalDate birthdate) implements Serializable {
}