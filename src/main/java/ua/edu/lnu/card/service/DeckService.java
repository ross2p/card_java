package ua.edu.lnu.card.service;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;


public interface DeckService {
    Page<DeckResponse> getPublicDecks(PageRequest pageRequest);

    Page<DeckResponse> getDecksByUserId(Long userId, PageRequest pageRequest);

    Page<DeckResponse> getPublicDecksByUserId(Long userId, PageRequest of);

    DeckResponse create(Long userId, DeckCreationUpdateRequest deckResponse);

    Page<DeckResponse> getDecksByCollaborator(Long userId, Long collaboratorId, PageRequest pageRequest);

    DeckResponse addCollaborator(Long deckId, CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);
    @Named("getDeckById")
    Deck getDeckById(Long id);

    DeckResponse update(Long deckId, DeckCreationUpdateRequest deckResponse);

    void delete(Long deckId);
}
