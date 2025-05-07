package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.edu.lnu.card.dtos.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.services.CollaboratorService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CollaboratorControllerTest {

    @Mock
    private CollaboratorService collaboratorService;

    @InjectMocks
    private CollaboratorController collaboratorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(collaboratorController).build();
    }

    @Test
    void createCollaborator_shouldReturnCollaborator() throws Exception {
        CollaboratorCreationUpdateRequest request = new CollaboratorCreationUpdateRequest(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Collaborator collaborator = new Collaborator();

        when(collaboratorService.createCollaborator(any(CollaboratorCreationUpdateRequest.class)))
                .thenReturn(collaborator);

        mockMvc.perform(post("/collaborator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(collaborator)));

        verify(collaboratorService).createCollaborator(any(CollaboratorCreationUpdateRequest.class));
    }

    @Test
    void getCollaboratorsByDeckId_shouldReturnList() throws Exception {
        UUID deckId = UUID.randomUUID();
        List<Collaborator> collaborators = List.of(new Collaborator(), new Collaborator());

        when(collaboratorService.getCollaboratorsByDeckId(deckId)).thenReturn(collaborators);

        mockMvc.perform(get("/collaborator/deck/" + deckId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(collaborators)));

        verify(collaboratorService).getCollaboratorsByDeckId(deckId);
    }

    @Test
    void deleteCollaborator_shouldReturnNoContent() throws Exception {
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        doNothing().when(collaboratorService).deleteCollaborator(deckId, userId);

        mockMvc.perform(delete("/collaborator")
                .param("deckId", deckId.toString())
                .param("userId", userId.toString()))
                .andExpect(status().isNoContent());

        verify(collaboratorService).deleteCollaborator(deckId, userId);
    }
}
