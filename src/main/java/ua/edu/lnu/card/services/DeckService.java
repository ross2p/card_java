package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dtos.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DeckResponse;
import ua.edu.lnu.card.entities.Deck;

import java.util.UUID;


public interface DeckService {

    Page<DeckResponse> getDecksByUserId(UUID userId, PageRequest pageRequest);

    Page<DeckResponse> getAllPublicDeck(PageRequest of);

    Deck createDeck(UUID userId, DeckCreationUpdateRequest deckResponse);

    Deck updateDeck(UUID deckId, DeckCreationUpdateRequest deckResponse);

    void deleteDeck(UUID deckId);

    DeckResponse getDeckDtoById(UUID deckId);

    Deck getDeckById(UUID deckId);
}
