package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.entities.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {
    CardData getCardDataById(UUID cardId);

    List<CardData> getAllByDeckId(UUID deckId);

    Card create(CardCreationUpdateRequest cardDto);

    CardData update(UUID cardId, CardCreationUpdateRequest cardDto);

    void delete(UUID cardId);
}
