package ua.edu.lnu.card.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.Deck;

import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID> {
}