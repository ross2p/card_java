package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DescriptionDeck;

public interface AIService {
    CardCreationUpdateRequest generateOneCard(CardCreationUpdateRequest cardCreationUpdateRequest) throws Exception;

    DescriptionDeck generateDescriptionDeck(DescriptionDeck descriptionDeck) throws Exception;
}
