package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DeckResponse;
import ua.edu.lnu.card.entities.Deck;
import ua.edu.lnu.card.exceptions.exception.server.NotImplemented;
import ua.edu.lnu.card.mappers.DeckMapper;
import ua.edu.lnu.card.repositories.DeckRepository;
import ua.edu.lnu.card.services.CollaboratorService;
import ua.edu.lnu.card.services.DeckRoleService;
import ua.edu.lnu.card.services.DeckService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    private final DeckRoleService deckRoleService;
    private final CollaboratorService collaboratorService;

    @Override
    public Deck getDeckById(UUID deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck with id %s not found".formatted(deckId)));
    }

    @Override
    public Page<DeckResponse> getDecksByUserId(UUID userId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<DeckResponse> getAllPublicDeck(PageRequest pageRequest) {
        Deck exampleDeck = new Deck();
        exampleDeck.setIsPrivate(false);

        Example<Deck> example = Example.of(exampleDeck, ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("isPrivate", ExampleMatcher.GenericPropertyMatchers.exact()));
        Page<Deck> pageDeck = deckRepository.findAll(example, pageRequest);
        return pageDeck
                .map(deckMapper::toDto);
    }

    @Override
    public Deck createDeck(UUID userId, DeckCreationUpdateRequest deckCreationUpdateRequest) {
        Deck deck = deckMapper.toEntity(deckCreationUpdateRequest, userId);
        System.out.println("start DeckServiceImpl.createDeck");
        Deck savedDeck = deckRepository.saveAndFlush(deck);

        System.out.println("DeckServiceImpl.createDeck: " + deckMapper.toDto(getDeckById(savedDeck.getId())));

        deckRoleService.createDefaultDeckRole(
                savedDeck.getId());
        collaboratorService.createOwnerCollaborator(
                savedDeck.getId(), userId);

        return savedDeck;
    }

    @Override
    public Deck updateDeck(UUID deckId, DeckCreationUpdateRequest deckCreationUpdateRequest) {
        Deck deck = getDeckById(deckId);
        Deck updatedDeck = deckMapper.partialUpdate(deckCreationUpdateRequest, deck);
        return deckRepository.save(updatedDeck);
    }

    @Override
    public void deleteDeck(UUID deckId) {
        throw new NotImplemented("Not implemented yet");
    }

    @Override
    public DeckResponse getDeckDtoById(UUID deckId) {
        return deckMapper.toDto(getDeckById(deckId));
    }
}