package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.exceptions.exception.client.NotFound;
import ua.edu.lnu.card.exceptions.exception.server.InternalServerError;
import ua.edu.lnu.card.mappers.CollaboratorMapper;
import ua.edu.lnu.card.repositories.CollaboratorRepository;
import ua.edu.lnu.card.services.CollaboratorService;
import ua.edu.lnu.card.services.DeckRoleService;
import ua.edu.lnu.card.utils.DefaultDeckRoles;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {
    private final CollaboratorRepository collaboratorRepository;
    private final CollaboratorMapper collaboratorMapper;
    private final DeckRoleService deckRoleService;

    @Override
    public Collaborator createOwnerCollaborator(UUID deckId, UUID userId) {
        DeckRole ownerRole = deckRoleService.getDeckRoleByDeckIdAndName(deckId, DefaultDeckRoles.OWNER.getName());
        return createCollaborator(new CollaboratorCreationUpdateRequest(deckId, userId, ownerRole.getId()));
    }

    @Override
    public Collaborator createCollaborator(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest) {
        System.out.println("CollaboratorServiceImpl >> createCollaborator >> collaboratorCreationUpdateRequest: "
                + collaboratorMapper.toEntity(collaboratorCreationUpdateRequest));
        return collaboratorRepository.save(collaboratorMapper.toEntity(collaboratorCreationUpdateRequest));
    }

    @Override
    public Collaborator updateCollaborator(UUID collaboratorId,
            CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest) {
        Collaborator collaborator = this.getCollaboratorById(collaboratorId);
        return collaboratorMapper.partialUpdate(collaboratorCreationUpdateRequest, collaborator);
    }

    @Override
    public List<Collaborator> getCollaboratorsByDeckId(UUID deckId) {
        return collaboratorRepository.findAllByDeckId(deckId);
    }

    @Override
    public void deleteCollaborator(UUID deckId, UUID userId) {
        throw new InternalServerError("CollaboratorService >> deleteCollaborator >>  Not implemented yet");
    }

    private Collaborator getCollaboratorById(UUID collaboratorId) {
        return collaboratorRepository.findById(collaboratorId)
                .orElseThrow(() -> new NotFound("Collaborator with id %s not found".formatted(collaboratorId)));
    }
}
