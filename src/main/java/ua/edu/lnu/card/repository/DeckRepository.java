package ua.edu.lnu.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Deck;


public interface DeckRepository extends JpaRepository<Deck, Long> {
    Page<Deck> findByIsprivateFalse(PageRequest pageRequest);
    Page<Deck> findByOwnerId(Long userId, PageRequest pageRequest);
}