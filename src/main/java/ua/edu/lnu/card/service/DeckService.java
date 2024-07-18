package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;

import java.util.List;

public interface DeckService {
    boolean isOwner(Long todoId, Long id);

    boolean isCollaborator(Long todoId, Long id);

    Page<DeckResponse> getPublicDecks(PageRequest pageRequest);

    Page<DeckResponse> getDecksByUserId(Long userId, PageRequest pageRequest);

    Page<DeckResponse> getPublicDecksByUserId(Long userId, PageRequest of);

    DeckResponse create(Long userId, DeckCreationUpdateRequest deckResponse, String byUser);
}
