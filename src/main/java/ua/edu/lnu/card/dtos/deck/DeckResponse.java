package ua.edu.lnu.card.dtos.deck;

import lombok.Builder;
import lombok.Data;
import ua.edu.lnu.card.dtos.user.UserResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Deck}
 */
@Data
@Builder
public class DeckResponse implements Serializable {
    UUID id;
    String name;
    String description;
    UserResponse owner;
    Boolean isPrivate;
    Date createdAt;
    Date updatedAt;
    Integer cardsCount;
    Double rating;
}