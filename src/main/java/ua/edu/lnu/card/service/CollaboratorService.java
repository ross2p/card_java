package ua.edu.lnu.card.service;

import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.DeckRole;

import java.util.List;
import java.util.UUID;

public interface CollaboratorService {
    Collaborator createOwnerCollaborator(UUID deckId, UUID userId);

    Collaborator saveCollaborator(UUID deckId, UUID userId, UUID deckRoleId);

    List<Collaborator> getCollaboratorsByDeckId(UUID deckId);

    void deleteCollaborator(UUID deckId, UUID userId);
}
