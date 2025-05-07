package ua.edu.lnu.card.dtos.collaborator;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Collaborator}
 */
@Value
public class CollaboratorCreationUpdateRequest implements Serializable {
    UUID deckId;
    UUID userId;
    UUID deckRoleId;
}