package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.service.DeckService;




@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;

    private  Deck getDeckById(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Deck with id '%d' not found".formatted(id)));
    }

    @Override
    public boolean isOwner(Long todoId, Long id) {
        return false; //todo
    }

    @Override
    public boolean isCollaborator(Long todoId, Long id) {
        return false; //todo
    }
}
