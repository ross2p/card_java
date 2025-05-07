package ua.edu.lnu.card.services;

import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;

import java.util.UUID;

public interface DeckRatingService {
    DeckRatingResponse save(DeckRatingCreationRequest deckRatingCreationRequest, UUID userId);

    DeckRatingResponse getDeckRatingById(UUID id);
}
