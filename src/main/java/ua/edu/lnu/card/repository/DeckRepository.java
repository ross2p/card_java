package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}