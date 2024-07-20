package ua.edu.lnu.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Deck;


public interface DeckRepository extends JpaRepository<Deck, Long> {
    Page<Deck> findByCollaborators_IdAndOwner_IdOrIsprivateTrueAndOwner_Id(Long id, Long id1, Long id2, Pageable pageable);
    Page<Deck> findByCollaborators_IdAndOwner_Id(Integer id, Long id1, Pageable pageable);
    boolean existsByOwner_IdAndId(Long id, Long id1);
    Page<Deck> findByIsprivateFalse(PageRequest pageRequest);
    Page<Deck> findByOwnerId(Long userId, PageRequest pageRequest);
}