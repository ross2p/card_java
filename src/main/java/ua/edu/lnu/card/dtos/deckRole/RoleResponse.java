package ua.edu.lnu.card.dto.deckRole;

import lombok.Value;
import ua.edu.lnu.card.dto.deck.DeckResponse;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.DeckRole}
 */
@Value
public class RoleResponse implements Serializable {
    UUID id;
    String name;
    DeckResponse deck;
    boolean isViewed;
    boolean isEditable;
    boolean isEditRoleUser;
}