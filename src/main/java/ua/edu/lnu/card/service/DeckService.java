package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;

import java.util.UUID;


public interface DeckService {

    Page<DeckResponse> getDecksByUserId(UUID userId, PageRequest pageRequest);

    Page<DeckResponse> getAllPublicDeck(PageRequest of);

    DeckResponse createDeck(UUID userId, DeckCreationUpdateRequest deckResponse);

    DeckResponse updateDeck(UUID deckId, DeckCreationUpdateRequest deckResponse);

    void deleteDeck(UUID deckId);

    DeckResponse getDeckDtoById(UUID deckId);

}
