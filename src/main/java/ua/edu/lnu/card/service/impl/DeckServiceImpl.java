package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.exception.exception.client.NotFound;
import ua.edu.lnu.card.mapper.DeckMapper;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.service.DeckService;
import ua.edu.lnu.card.utils.enums.AccessLevel;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    private Deck getDeckById(UUID deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck with id %s not found".formatted(deckId)));
    }

    @Override
    public Page<DeckResponse> getDecksByUserId(UUID userId, PageRequest pageRequest) {

        return deckRepository.findDecksByOwnerOrCollaboratorWithStatus(userId, AccessLevel.EDIT, pageRequest).map(deckMapper::toDto);
    }

    @Override
    public Page<DeckResponse> getAllPublic(PageRequest pageRequest) {
        Deck exampleDeck = new Deck();
        exampleDeck.setIsPrivate(false); // Вибираємо лише публічні деки

        Example<Deck> example = Example.of(exampleDeck, ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("isPrivate", ExampleMatcher.GenericPropertyMatchers.exact())); // Точний збіг для поля isPrivate

        return deckRepository.findAll(example, pageRequest)
                .map(deckMapper::toDto);
    }

    @Override
    public DeckResponse create(UUID userId, DeckCreationUpdateRequest deckResponse) {
        Deck deck = deckMapper.toEntity(deckResponse, userId);
        Deck savedDeck = deckRepository.save(deck);
        return deckMapper.toDto(savedDeck);
    }

    @Override
    public Page<DeckResponse> getDecksByCollaborator(UUID userId, UUID collaboratorId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public DeckResponse update(UUID deckId, DeckCreationUpdateRequest deckResponse) {
        Deck deck = getDeckById(deckId);

        Deck updatedDeck = deckMapper.partialUpdate(deckResponse, deck);
        return deckMapper.toDto(deckRepository.save(updatedDeck));
    }

    @Override
    public void delete(UUID deckId) {

    }

    @Override
    public DeckResponse getById(UUID deckId) {
        return deckRepository.findById(deckId)
                .map(deckMapper::toDto)
                .orElseThrow(() -> new NotFound("Deck with id %s not found".formatted(deckId)));
    }
}
