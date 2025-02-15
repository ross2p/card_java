package ua.edu.lnu.card.service;

import ua.edu.lnu.card.entity.DeckRating;

import java.util.UUID;

public interface DeckRatingService {
    DeckRating save(UUID deckId, Double rating, UUID userId);

}
