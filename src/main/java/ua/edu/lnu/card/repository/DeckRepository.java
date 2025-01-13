package ua.edu.lnu.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.utils.enums.AccessLevel;

import java.util.Map;
import java.util.UUID;


public interface DeckRepository extends JpaRepository<Deck, UUID> {
    @Query("""
        SELECT d FROM Deck d
        LEFT JOIN d.collaborator c
        WHERE d.owner.id = :userId
        OR (KEY(c).id = :userId AND c = :accessLevel)
    """)
    Page<Deck> findDecksByOwnerOrCollaboratorWithStatus(@Param("userId") UUID userId, @Param("accessLevel") AccessLevel accessLevel, Pageable pageable);
}