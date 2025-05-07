package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.lnu.card.dtos.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.mappers.CollaboratorMapper;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.service.impl.CollaboratorServiceImpl;
import ua.edu.lnu.card.utils.DefaultDeckRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollaboratorServiceTest {

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private CollaboratorMapper collaboratorMapper;

    @Mock
    private DeckRoleService deckRoleService;

    private CollaboratorService collaboratorService;

    @BeforeEach
    void setUp() {
        collaboratorService = new CollaboratorServiceImpl(
                collaboratorRepository,
                collaboratorMapper,
                deckRoleService);
    }

    @Test
    void createOwnerCollaborator_shouldCreateAndReturnCollaborator() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        DeckRole ownerRole = new DeckRole();
        ownerRole.setId(roleId);
        ownerRole.setName(DefaultDeckRoles.OWNER.getName());

        CollaboratorCreationUpdateRequest request = new CollaboratorCreationUpdateRequest(deckId, userId, roleId);
        Collaborator collaborator = new Collaborator();
        Collaborator expectedCollaborator = new Collaborator();

        when(deckRoleService.getDeckRoleByDeckIdAndName(deckId, DefaultDeckRoles.OWNER.getName()))
                .thenReturn(ownerRole);
        when(collaboratorMapper.toEntity(request)).thenReturn(collaborator);
        when(collaboratorRepository.save(collaborator)).thenReturn(expectedCollaborator);

        // Act
        Collaborator result = collaboratorService.createOwnerCollaborator(deckId, userId);

        // Assert
        assertThat(result).isEqualTo(expectedCollaborator);
        verify(deckRoleService).getDeckRoleByDeckIdAndName(deckId, DefaultDeckRoles.OWNER.getName());
        verify(collaboratorMapper, times(2)).toEntity(request);
        verify(collaboratorRepository).save(collaborator);
    }

    @Test
    void createCollaborator_shouldCreateAndReturnCollaborator() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        CollaboratorCreationUpdateRequest request = new CollaboratorCreationUpdateRequest(deckId, userId, roleId);
        Collaborator collaborator = new Collaborator();
        Collaborator expectedCollaborator = new Collaborator();

        when(collaboratorMapper.toEntity(request)).thenReturn(collaborator);
        when(collaboratorRepository.save(collaborator)).thenReturn(expectedCollaborator);

        // Act
        Collaborator result = collaboratorService.createCollaborator(request);

        // Assert
        assertThat(result).isEqualTo(expectedCollaborator);
        verify(collaboratorMapper, times(2)).toEntity(request);
        verify(collaboratorRepository).save(collaborator);
    }

    @Test
    void updateCollaborator_shouldUpdateAndReturnCollaborator() {
        // Arrange
        UUID collaboratorId = UUID.randomUUID();
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        CollaboratorCreationUpdateRequest request = new CollaboratorCreationUpdateRequest(deckId, userId, roleId);
        Collaborator existingCollaborator = new Collaborator();
        existingCollaborator.setId(collaboratorId);
        Collaborator updatedCollaborator = new Collaborator();
        updatedCollaborator.setId(collaboratorId);

        when(collaboratorRepository.findById(collaboratorId)).thenReturn(Optional.of(existingCollaborator));
        when(collaboratorMapper.partialUpdate(request, existingCollaborator)).thenReturn(updatedCollaborator);

        // Act
        Collaborator result = collaboratorService.updateCollaborator(collaboratorId, request);

        // Assert
        assertThat(result).isEqualTo(updatedCollaborator);
        verify(collaboratorRepository).findById(collaboratorId);
        verify(collaboratorMapper).partialUpdate(request, existingCollaborator);
    }

    @Test
    void getCollaboratorsByDeckId_shouldReturnListOfCollaborators() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        List<Collaborator> expectedCollaborators = List.of(new Collaborator(), new Collaborator());

        when(collaboratorRepository.findAllByDeckId(deckId)).thenReturn(expectedCollaborators);

        // Act
        List<Collaborator> result = collaboratorService.getCollaboratorsByDeckId(deckId);

        // Assert
        assertThat(result).isEqualTo(expectedCollaborators);
        verify(collaboratorRepository).findAllByDeckId(deckId);
    }

    @Test
    void deleteCollaborator_shouldThrowInternalServerError() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        // Act & Assert
        assertThatThrownBy(() -> collaboratorService.deleteCollaborator(deckId, userId))
                .isInstanceOf(InternalServerError.class)
                .hasMessageContaining("Not implemented yet");
    }
}