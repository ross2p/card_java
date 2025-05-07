package ua.edu.lnu.card.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Card}
 */
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CardCreationUpdateRequest implements Serializable {
    UUID deckId;
    String key;
    String value;
    String description;
}