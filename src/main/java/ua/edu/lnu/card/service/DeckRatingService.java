package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dto.deck.DeckRatingResponse;
import ua.edu.lnu.card.entity.DeckRating;

import java.util.UUID;

public interface DeckRatingService {
    DeckRatingResponse save(DeckRatingCreationRequest deckRatingCreationRequest, UUID userId);

    DeckRatingResponse getDeckRatingById(UUID id);
}
