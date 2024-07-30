package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.AccessLevel;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.mapper.CollaboratorMapper;
import ua.edu.lnu.card.mapper.DeckMapper;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.service.DeckService;


@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final CollaboratorMapper collaboratorMapper;
    private final DeckMapper deckMapper;

    public Deck getDeckById(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deck with id '%d' not found".formatted(id)));
    }

    @Override
    public Page<DeckResponse> getPublicDecks(PageRequest pageRequest) {
        return deckRepository.findByIsprivateFalse(pageRequest).map(deckMapper::toDto);
    }

    @Override
    public Page<DeckResponse> getDecksByUserId(Long userId, PageRequest pageRequest) {
        return deckRepository.findByOwnerId(userId, pageRequest).map(deckMapper::toDto);
    }

    @Override
    public Page<DeckResponse> getPublicDecksByUserId(Long userId, PageRequest pageRequest) {
        return deckRepository.findByIsprivateFalse(pageRequest).map(deckMapper::toDto);

    }

    @Override
    public DeckResponse create(Long userId, DeckCreationUpdateRequest deckResponse) {

        Deck newDeck = deckMapper.toEntity(deckResponse, userId);

        Collaborator collaborator = collaboratorMapper.toEntity(userId, AccessLevel.OWNER);
        collaborator.setDeck(newDeck);
        newDeck.getCollaborators().add(collaborator);

        newDeck = deckRepository.save(newDeck);
        return deckMapper.toDto(newDeck);
    }


    @Override
    public Page<DeckResponse> getDecksByCollaborator(Long userId, Long collaboratorId, PageRequest pageRequest) {
        return deckRepository
                .findByCollaborators_IdAndOwner_IdOrIsprivateTrueAndOwner_Id(collaboratorId, userId, userId, pageRequest)
                .map(deckMapper::toDto);
    }

    @Override
    public DeckResponse addCollaborator(Long deckId, CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest) {

        Collaborator collaborator = collaboratorMapper.toEntity(collaboratorCreationUpdateRequest);
        Deck deck = getDeckById(deckId);

        collaborator.setDeck(deck);
        deck.getCollaborators().add(collaborator);

        Deck updatedDeck = deckRepository.save(deck);

        return deckMapper.toDto(updatedDeck);
    }

    @Override
    public DeckResponse update(Long deckId, DeckCreationUpdateRequest deckResponse) {
        Deck deckToUpdate = getDeckById(deckId);

        Deck updatedDeck = deckMapper.partialUpdate(deckResponse, deckToUpdate);

        updatedDeck = deckRepository.save(updatedDeck);

        return deckMapper.toDto(updatedDeck);
    }

    @Override
    public void delete(Long deckId) {
        Deck deck = getDeckById(deckId);
        collaboratorRepository.deleteAll(deck.getCollaborators());
        deckRepository.delete(deck);
    }

}
