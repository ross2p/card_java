package ua.edu.lnu.card.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.DeckRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRoleRepository extends JpaRepository<DeckRole, UUID> {
    List<DeckRole> findAllByDeckId(UUID deckId);

    Optional<DeckRole> findByDeckIdAndName(UUID deckId, String name);
}