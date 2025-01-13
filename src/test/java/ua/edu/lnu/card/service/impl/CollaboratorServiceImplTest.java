package ua.edu.lnu.card.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.edu.lnu.card.repository.CollaboratorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollaboratorServiceImplTest {

    @InjectMocks
    private CollaboratorServiceImpl collaboratorService;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsCollaborator_Exists() {
        Long userId = 1L;
        Long deckId = 2L;

        when(collaboratorRepository.existsByUser_IdAndDeck_Id(userId, deckId)).thenReturn(true);

        boolean result = collaboratorService.isCollaborator(userId, deckId);

        assertTrue(result, "Expected user to be a collaborator.");
        verify(collaboratorRepository).existsByUser_IdAndDeck_Id(userId, deckId);
    }

    @Test
    public void testIsCollaborator_NotExists() {
        Long userId = 1L;
        Long deckId = 2L;

        when(collaboratorRepository.existsByUser_IdAndDeck_Id(userId, deckId)).thenReturn(false);

        boolean result = collaboratorService.isCollaborator(userId, deckId);

        assertFalse(result, "Expected user not to be a collaborator.");
        verify(collaboratorRepository).existsByUser_IdAndDeck_Id(userId, deckId);
    }
}