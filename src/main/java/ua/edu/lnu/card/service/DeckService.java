package ua.edu.lnu.card.service;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;

import java.util.UUID;


public interface DeckService {

    Page<DeckResponse> getDecksByUserId(UUID userId, PageRequest pageRequest);

    Page<DeckResponse> getAllPublic(PageRequest of);

    DeckResponse create(UUID userId, DeckCreationUpdateRequest deckResponse);

    Page<DeckResponse> getDecksByCollaborator(UUID userId, UUID collaboratorId, PageRequest pageRequest);

    DeckResponse update(UUID deckId, DeckCreationUpdateRequest deckResponse);

    void delete(UUID deckId);

    DeckResponse getById(UUID deckId);
}
