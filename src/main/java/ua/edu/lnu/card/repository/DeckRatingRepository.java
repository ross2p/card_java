package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.DeckRating;

import java.util.UUID;

public interface DeckRatingRepository extends JpaRepository<DeckRating, UUID> {
}