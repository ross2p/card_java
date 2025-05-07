package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.edu.lnu.card.controller.CardController;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.entities.Card;
import ua.edu.lnu.card.services.CardService;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    void getAllCardByDeckId_shouldReturnCardData() throws Exception {
        UUID cardId = UUID.randomUUID();
        CardData cardData = new CardData(cardId, "key", "value", "desc", new Date(), new Date());

        when(cardService.getCardDataById(cardId)).thenReturn(cardData);

        mockMvc.perform(get("/card/" + cardId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cardData)));

        verify(cardService).getCardDataById(cardId);
    }

    @Test
    void create_shouldReturnCard() throws Exception {
        UUID deckId = UUID.randomUUID();
        CardCreationUpdateRequest request = new CardCreationUpdateRequest(deckId, "key", "value", "desc");
        Card card = new Card();
        card.setId(UUID.randomUUID());

        when(cardService.create(any(CardCreationUpdateRequest.class))).thenReturn(card);

        mockMvc.perform(post("/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(card)));

        verify(cardService).create(any(CardCreationUpdateRequest.class));
    }
}
