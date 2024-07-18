package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Collaborator;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}