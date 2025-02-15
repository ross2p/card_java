package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.service.CollaboratorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

    @Override
    public Collaborator createOwnerCollaborator(UUID deckId, UUID userId) {
        return null;
    }

    @Override
    public Collaborator saveCollaborator(UUID deckId, UUID userId, UUID deckRoleId) {
        return null;
    }

    @Override
    public List<Collaborator> getCollaboratorsByDeckId(UUID deckId) {
        return null;
    }

    @Override
    public void deleteCollaborator(UUID deckId, UUID userId) {

    }

}
