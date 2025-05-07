package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.edu.lnu.card.dtos.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.service.DeckRoleService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeckRoleControllerTest {

    @Mock
    private DeckRoleService deckRoleService;

    @InjectMocks
    private DeckRoleController deckRoleController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deckRoleController).build();
    }

    @Test
    void createDeckRole_shouldReturnDeckRole() throws Exception {
        DeckRoleCreationUpdateRequest request = new DeckRoleCreationUpdateRequest();
        DeckRole deckRole = new DeckRole();

        when(deckRoleService.createDeckRole(any(DeckRoleCreationUpdateRequest.class))).thenReturn(deckRole);

        mockMvc.perform(post("/deck-role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deckRole)));

        verify(deckRoleService).createDeckRole(any(DeckRoleCreationUpdateRequest.class));
    }
}
