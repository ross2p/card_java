package ua.edu.lnu.card.dtos.deckRole;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.DeckRole}
 */

@Data
public class DeckRoleCreationUpdateRequest implements Serializable {
    String name;
    UUID deckId;
    Boolean isViewed;
    Boolean isEditable;
    Boolean isEditRoleUser;
}