package ua.edu.lnu.card.dto.deck;

import lombok.Data;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Collaborator;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Data
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
    Set<Collaborator> collaborators;

}