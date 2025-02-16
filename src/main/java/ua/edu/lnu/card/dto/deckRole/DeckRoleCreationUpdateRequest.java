package ua.edu.lnu.card.dto.deckRole;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.DeckRole}
 */

@Data
public class DeckRoleCreationUpdateRequest implements Serializable {
    String name;
    UUID deckId;
    Boolean isViewed;
    Boolean isEditable;
    Boolean isEditRoleUser;
}