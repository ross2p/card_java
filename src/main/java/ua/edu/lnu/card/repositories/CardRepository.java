package ua.edu.lnu.card.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByDeck_IdOrderByCreatedAtAsc(UUID id);
}