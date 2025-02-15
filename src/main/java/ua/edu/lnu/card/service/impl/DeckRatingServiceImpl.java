package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.entity.DeckRating;
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
    public DeckRating save(UUID deckId, Double rating, UUID userId) {
        return deckRatingRepository.save(deckRatingMapper.toEntity(rating, deckId, userId));
    }


}
