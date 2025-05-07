package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dto.deck.DeckRatingResponse;
import ua.edu.lnu.card.mapper.DeckRatingMapper;
import ua.edu.lnu.card.repository.DeckRatingRepository;
import ua.edu.lnu.card.service.DeckRatingService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckRatingServiceImpl implements DeckRatingService {
    private final DeckRatingRepository deckRatingRepository;
    private final DeckRatingMapper deckRatingMapper;

    @Override
    public DeckRatingResponse save(DeckRatingCreationRequest deckRatingCreationRequest, UUID userId) {
//        return deckRatingRepository.save(deckRatingMapper.toEntity(rating, deckId, userId));
        return null;
    }

    @Override
    public DeckRatingResponse getDeckRatingById(UUID id) {
        return deckRatingRepository.findById(id)
                .map(deckRatingMapper::toDto)
                .orElse(null);
    }
}
