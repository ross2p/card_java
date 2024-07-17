package ua.edu.lnu.card.dto.deck;

import ua.edu.lnu.card.dto.user.UserResponse;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
public record DeckResponse(Long id, String name, String description, UserResponse owner,
                           Boolean isprivate) implements Serializable {
}