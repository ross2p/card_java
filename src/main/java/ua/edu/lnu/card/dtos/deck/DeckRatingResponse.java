package ua.edu.lnu.card.dtos.deck;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.DeckRating}
 */
@Value
@Builder
public class DeckRatingResponse implements Serializable {
    UUID id;
    UUID deckId;
    UUID userId;
    Double userRating;
    Date createdAt;
    Date updatedAt;
    Double rating;
}