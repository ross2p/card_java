package ua.edu.lnu.card.dto.deck;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Value
public class DeckCreationUpdateRequest implements Serializable {
    String name;
    String description;
    Boolean isPrivate;
}