package ua.edu.lnu.card.dtos.deckRole;

import lombok.Value;
import ua.edu.lnu.card.dtos.deck.DeckResponse;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.DeckRole}
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