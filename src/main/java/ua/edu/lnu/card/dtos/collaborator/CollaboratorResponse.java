package ua.edu.lnu.card.dtos.collaborator;

import lombok.Value;
import ua.edu.lnu.card.dtos.user.UserResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Collaborator}
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