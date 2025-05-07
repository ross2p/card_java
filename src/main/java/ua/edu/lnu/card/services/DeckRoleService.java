package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dtos.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entities.DeckRole;

import java.util.List;
import java.util.UUID;

public interface DeckRoleService {
    List<DeckRole> createDefaultDeckRole(UUID deckId);

    DeckRole createDeckRole(DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest);

    List<DeckRole> getDeckRolesByDeckId(UUID deckId);

    DeckRole updateDeckRole(UUID deckRoleId, DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest);

    void deleteDeckRole(UUID deckRoleId);

    DeckRole getDeckRoleById(UUID deckRoleId);

    DeckRole getDeckRoleByDeckIdAndName(UUID deckId, String name);
}
