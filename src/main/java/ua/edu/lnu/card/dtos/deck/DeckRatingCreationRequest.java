package ua.edu.lnu.card.dtos.deck;

import lombok.Data;
import lombok.Value;
import java.util.UUID;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entities.DeckRating}
 */
@Value
@Data
public class DeckRatingCreationRequest implements Serializable {
    Double rating;
    UUID deckId;
}