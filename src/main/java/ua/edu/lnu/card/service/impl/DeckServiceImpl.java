package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.CollaboratorResponse;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.AccessLevel;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.mapper.CollaboratorMapper;
import ua.edu.lnu.card.mapper.DeckMapper;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.DeckService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final CollaboratorMapper collaboratorMapper;
    private final DeckMapper deckMapper;

    private Deck getDeckById(Long id) {
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
        return deckRepository.findByIsprivateFalse(pageRequest).map(deckMapper::toDto); //todo fix

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
    public Page<DeckResponse> getDecksByCollaborator(Long userId, Long collaboratorId, PageRequest pageRequest) {

        return deckRepository.findByCollaborators_IdAndOwner_IdOrIsprivateTrueAndOwner_Id(collaboratorId, userId, userId, pageRequest).map(deckMapper::toDto);
    }

    @Override
    public Set<CollaboratorResponse> addCollaborator(Long deckId, CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest) {
////        Collaborator collaborator = userRepository.findById(collaboratorCreationUpdateRequest.userId())
////                .map(user -> collaboratorRepository.save(Collaborator.builder()
////                        .user(user)
////                        .deck(getDeckById(deckId))
////                        .accessLevel(collaboratorCreationUpdateRequest.accessLevel())
////                        .build()))
////                .orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(collaboratorCreationUpdateRequest.userId())));
////        Deck deck = getDeckById(deckId);
//        Collaborator collaborator = collaboratorMapper.toEntity(collaboratorCreationUpdateRequest, deckId);
////        collaborator = collaboratorRepository.save(collaborator);
//        System.out.println(collaborator);
//
//        deckRepository.findById(deckId).get().getCollaborators().add(collaborator);
//
//        return deckRepository.findById(deckId).get().getCollaborators().stream().map(collaboratorMapper::toDto).collect(Collectors.toSet());
        return null;
    }

}
