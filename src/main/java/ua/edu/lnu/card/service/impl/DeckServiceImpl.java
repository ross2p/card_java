package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.AccessLevel;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.mapper.DeckMapper;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.DeckService;

import java.time.Instant;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final DeckMapper deckMapper;

    private Deck getDeckById(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deck with id '%d' not found".formatted(id)));
    }

    @Override
    public boolean isOwner(Long todoId, Long id) {
        return false;       //todo
    }

    @Override
    public boolean isCollaborator(Long todoId, Long id) {
        return false;       //todo
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
    public DeckResponse create(Long userId, DeckCreationUpdateRequest deckResponse, String byUser) {

        Deck newDeck = deckMapper.toEntity(deckResponse);

        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(userId)));
        newDeck.setOwner(owner);
        newDeck.setUpdatedBy(byUser);
        newDeck.setUpdatedOn(Instant.now());
        newDeck = deckRepository.save(newDeck);

        Collaborator collaborator =  collaboratorRepository.save(Collaborator.builder()
                                        .user(owner)
                                        .deck(newDeck)
                                        .accessLevel(AccessLevel.OWNER)
                                        .build());
        newDeck.getCollaborators().add(collaborator);

        return deckMapper.toDto(newDeck);
    }

    @Override
    public List<DeckResponse> getAll() {
        return deckRepository.findAll().stream().map(deckMapper::toDto).toList();
    }


}
