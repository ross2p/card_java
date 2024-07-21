package ua.edu.lnu.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findAllByDeckId(Long deckId, PageRequest pageRequest);
}