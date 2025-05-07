package ua.edu.lnu.card.dto.card;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Card}
 */
@Value
@AllArgsConstructor
public class CardData implements Serializable {
    UUID id;
    String key;
    String value;
    String description;
    Date createdAt;
    Date updatedAt;
}