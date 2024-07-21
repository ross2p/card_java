package ua.edu.lnu.card.dto.collaborator;

import lombok.Builder;
import ua.edu.lnu.card.entity.AccessLevel;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Collaborator}
 */
@Builder
public record CollaboratorCreationUpdateRequest(Long userId, AccessLevel accessLevel) implements Serializable {
}