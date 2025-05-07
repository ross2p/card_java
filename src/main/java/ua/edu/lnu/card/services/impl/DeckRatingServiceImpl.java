package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;
import ua.edu.lnu.card.entities.DeckRating;
import ua.edu.lnu.card.exceptions.exception.client.NotFound;
import ua.edu.lnu.card.mappers.DeckRatingMapper;
import ua.edu.lnu.card.repositories.DeckRatingRepository;
import ua.edu.lnu.card.services.DeckRatingService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckRatingServiceImpl implements DeckRatingService {
    private final DeckRatingRepository deckRatingRepository;
    private final DeckRatingMapper deckRatingMapper;

    @Override
    public DeckRatingResponse save(DeckRatingCreationRequest deckRatingCreationRequest, UUID userId) {
        DeckRating deckRating = deckRatingRepository.save(
                deckRatingMapper.toEntity(deckRatingCreationRequest.getRating(), deckRatingCreationRequest.getDeckId(),
                        userId));
        return deckRatingMapper.toDto(deckRating);
    }

    @Override
    public DeckRatingResponse getDeckRatingById(UUID id) {
        return deckRatingRepository.findById(id)
                .map(deckRatingMapper::toDto)
                .orElseThrow(() -> new NotFound("Deck rating with id %s not found".formatted(id)));
    }
}
