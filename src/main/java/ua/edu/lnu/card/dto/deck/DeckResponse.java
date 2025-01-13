package ua.edu.lnu.card.dto.deck;

import lombok.Value;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.utils.enums.AccessLevel;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Value
public class DeckResponse implements Serializable {
    UUID id;
    String name;
    String description;
    UserResponse owner;
    Boolean isPrivate;
    Map<UserResponse, AccessLevel> userAccessLevel;
    Date createdAt;
    Date updatedAt;
    Set<CardData> cards;
}