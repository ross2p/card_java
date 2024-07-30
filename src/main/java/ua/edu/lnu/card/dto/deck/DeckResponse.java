package ua.edu.lnu.card.dto.deck;

import lombok.Builder;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@Builder
public record DeckResponse(Long id, String name, String description, Boolean isprivate,
                           Set<CollaboratorResponse> collaborators) implements Serializable {
}