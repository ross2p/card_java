package ua.edu.lnu.card.dto.deck;

import lombok.Data;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Value
@Data
public class DescriptionDeck implements Serializable {
    UUID id;
    String description;
}