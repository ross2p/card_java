package ua.edu.lnu.card.dto.deck;

import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Builder
public record DeckCreationUpdateRequest(String name, String description, Boolean isprivate) implements Serializable {
}