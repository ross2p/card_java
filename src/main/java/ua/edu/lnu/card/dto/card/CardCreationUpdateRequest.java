package ua.edu.lnu.card.dto.card;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Card}
 */
@Value
public class CardCreationUpdateRequest implements Serializable {
    UUID deckId;
    String key;
    String value;
    String description;
}