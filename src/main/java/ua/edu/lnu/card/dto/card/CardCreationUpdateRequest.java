package ua.edu.lnu.card.dto.card;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Card}
 */
public record CardCreationUpdateRequest(String key, String value, String description) implements Serializable {
}