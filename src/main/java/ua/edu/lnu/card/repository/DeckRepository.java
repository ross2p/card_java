package ua.edu.lnu.card.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Deck;

import java.util.UUID;


public interface DeckRepository extends JpaRepository<Deck, UUID> {
}