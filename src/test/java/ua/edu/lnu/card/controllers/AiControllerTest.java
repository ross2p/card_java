package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.edu.lnu.card.controller.AiController;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DescriptionDeck;
import ua.edu.lnu.card.services.AIService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AiControllerTest {

    @Mock
    private AIService aiService;

    @InjectMocks
    private AiController aiController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(aiController).build();
    }

    @Test
    void generateOneCard_shouldReturnCardCreationUpdateRequest() throws Exception {
        CardCreationUpdateRequest request = CardCreationUpdateRequest.builder()
                .deckId(java.util.UUID.randomUUID())
                .key("key")
                .value("value")
                .description("desc")
                .build();

        when(aiService.generateOneCard(any(CardCreationUpdateRequest.class))).thenReturn(request);

        mockMvc.perform(post("/ai/generate-card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(request)));

        verify(aiService).generateOneCard(any(CardCreationUpdateRequest.class));
    }

    @Test
    void generateDescriptionDeck_shouldReturnDescriptionDeck() throws Exception {
        DescriptionDeck deck = DescriptionDeck.builder()
                .id(java.util.UUID.randomUUID())
                .description("desc")
                .build();

        when(aiService.generateDescriptionDeck(any(DescriptionDeck.class))).thenReturn(deck);

        mockMvc.perform(post("/ai/generate-description-deck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deck)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deck)));

        verify(aiService).generateDescriptionDeck(any(DescriptionDeck.class));
    }
}
