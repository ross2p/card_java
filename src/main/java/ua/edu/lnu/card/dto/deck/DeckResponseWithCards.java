package ua.edu.lnu.card.dto.deck;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ua.edu.lnu.card.dto.card.CardData;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Deck}
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class DeckResponseWithCards extends DeckResponse implements Serializable {
    List<CardData> cards = new LinkedList<>();
}