package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.entity.DeckRole;
import ua.edu.lnu.card.service.DeckRoleService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckRoleServiceImpl implements DeckRoleService {

    @Override
    public List<DeckRole> createDefaultDeckRole(UUID deckId) {
        return null;
    }

    @Override
    public DeckRole createDeckRole(UUID deckId, String name) {
        return null;
    }

    @Override
    public List<DeckRole> getDeckRolesByDeckId(UUID deckId) {
        return null;
    }

    @Override
    public DeckRole updateDeckRole(UUID deckRoleId, String name) {
        return null;
    }

    @Override
    public void deleteDeckRole(UUID deckRoleId) {

    }
}
