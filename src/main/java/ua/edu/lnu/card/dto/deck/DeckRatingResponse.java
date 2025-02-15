package ua.edu.lnu.card.dto.deck;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.DeckRating}
 */
@Value
public class DeckRatingResponse implements Serializable {
    UUID id;
    UUID deckId;
    UUID userId;
    Double userRating;
    Date createdAt;
    Date updatedAt;
    Double rating;
}