package ua.edu.lnu.card.dto.deck;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Value
@Data
@Builder
public class DescriptionDeck implements Serializable {
    UUID id;
    String description;
}