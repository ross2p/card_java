package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.CollaboratorResponse;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;

import java.util.List;
import java.util.Set;

public interface DeckService {
    Page<DeckResponse> getPublicDecks(PageRequest pageRequest);

    Page<DeckResponse> getDecksByUserId(Long userId, PageRequest pageRequest);

    Page<DeckResponse> getPublicDecksByUserId(Long userId, PageRequest of);

    DeckResponse create(Long userId, DeckCreationUpdateRequest deckResponse, String byUser);

    Page<DeckResponse> getDecksByCollaborator(Long userId, Long collaboratorId, PageRequest pageRequest);

    Set<CollaboratorResponse> addCollaborator(Long deckId, CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);
}
