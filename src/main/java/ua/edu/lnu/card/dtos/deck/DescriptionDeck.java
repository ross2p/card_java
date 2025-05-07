package ua.edu.lnu.card.dtos.deck;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Deck}
 */
@Value
@Data
@Builder
public class DescriptionDeck implements Serializable {
    UUID id;
    String description;
}