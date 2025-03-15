package ua.edu.lnu.card.dto.collaborator;

import lombok.Value;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.DeckRole;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Collaborator}
 */
@Value
public class CollaboratorResponse implements Serializable {
    UUID id;
    UUID deckId;
    UserResponse user;
    UUID deckRoleId;
    Date createdAt;
    Date updatedAt;
}