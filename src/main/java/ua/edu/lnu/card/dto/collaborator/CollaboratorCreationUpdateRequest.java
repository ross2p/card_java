package ua.edu.lnu.card.dto.collaborator;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Collaborator}
 */
@Value
public class CollaboratorCreationUpdateRequest implements Serializable {
    UUID deckId;
    UUID userId;
    UUID deckRoleId;
}