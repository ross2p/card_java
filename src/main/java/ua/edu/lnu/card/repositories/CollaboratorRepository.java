package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.Collaborator;

import java.util.List;
import java.util.UUID;

public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {
    List<Collaborator> findAllByDeckId(UUID deckId);
}