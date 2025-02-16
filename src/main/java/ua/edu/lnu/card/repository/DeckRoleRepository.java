package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.entity.DeckRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRoleRepository extends JpaRepository<DeckRole, UUID> {
    List<DeckRole> findAllByDeckId(UUID deckId);

    Optional<DeckRole> findByDeckIdAndName(UUID deckId, String name);
}