package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.DeckRole;

import java.util.List;
import java.util.UUID;

public interface CollaboratorService {
    Collaborator createOwnerCollaborator(UUID deckId, UUID userId);

    Collaborator createCollaborator(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);

    Collaborator updateCollaborator(UUID collaboratorId, CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);

    List<Collaborator> getCollaboratorsByDeckId(UUID deckId);

    void deleteCollaborator(UUID deckId, UUID userId);
}
