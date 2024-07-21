package ua.edu.lnu.card.dto.deck;

import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.AccessLevel;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Collaborator}
 */
public record CollaboratorResponse(Long id, UserResponse user, AccessLevel accessLevel) implements Serializable {
}