package ua.edu.lnu.card.dtos.deck;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Deck}
 */
@Value
@Builder
public class DeckCreationUpdateRequest implements Serializable {
    String name;
    String description;
    Boolean isPrivate;
}