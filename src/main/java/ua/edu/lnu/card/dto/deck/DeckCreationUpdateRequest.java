package ua.edu.lnu.card.dto.deck;

import lombok.Value;
import ua.edu.lnu.card.utils.enums.AccessLevel;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Value
public class DeckCreationUpdateRequest implements Serializable {
    String name;
    String description;
    Boolean isPrivate;
    Map<UUID, AccessLevel> userAccessLevel;
}