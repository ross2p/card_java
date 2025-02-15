package ua.edu.lnu.card.service;

import ua.edu.lnu.card.entity.DeckRole;

import java.util.List;
import java.util.UUID;

public interface DeckRoleService {
    List<DeckRole> createDefaultDeckRole(UUID deckId);

    DeckRole createDeckRole(UUID deckId, String name);

    List<DeckRole> getDeckRolesByDeckId(UUID deckId);

    DeckRole updateDeckRole(UUID deckRoleId, String name);

    void deleteDeckRole(UUID deckRoleId);
}
