package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;
import ua.edu.lnu.card.entities.DeckRating;
import ua.edu.lnu.card.mappers.DeckRatingMapper;
import ua.edu.lnu.card.repository.DeckRatingRepository;
import ua.edu.lnu.card.service.DeckRatingService;
import ua.edu.lnu.card.exception.exception.client.NotFound;

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
