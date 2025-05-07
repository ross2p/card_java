package ua.edu.lnu.card.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.DeckRating;

import java.util.UUID;

public interface DeckRatingRepository extends JpaRepository<DeckRating, UUID> {
}