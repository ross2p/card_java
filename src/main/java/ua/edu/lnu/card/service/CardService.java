package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse readById(Long cardId);


    Page<CardResponse> getAllByDeckId(Long deckId, PageRequest pageRequest);

    CardResponse create(Long deckId, CardCreationUpdateRequest cardDto);

    CardResponse update(Long cardId, CardCreationUpdateRequest cardDto);

    void delete(Long cardId);
}
