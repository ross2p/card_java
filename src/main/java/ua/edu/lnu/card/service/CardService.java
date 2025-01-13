package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;

import java.util.List;
import java.util.UUID;


public interface CardService {
    CardData readById(UUID cardId);
    List<CardData> getAllByDeckId(UUID deckId);

    Card create(CardCreationUpdateRequest cardDto);

    CardData update(UUID cardId, CardCreationUpdateRequest cardDto);

    void delete(UUID cardId);
}
