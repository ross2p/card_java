package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.edu.lnu.card.api.AiClient;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.dto.deck.DescriptionDeck;
import ua.edu.lnu.card.service.impl.AIServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AiServiceTest {

    @Mock
    private DeckService deckService;
    @Mock
    private CardService cardService;
    @Mock
    private AiClient aiClient;

    @InjectMocks
    private AIServiceImpl aiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aiService = new AIServiceImpl(deckService, cardService, aiClient);
    }

    @Test
    void generateOneCard_shouldReturnCardCreationUpdateRequest() throws Exception {
        UUID deckId = UUID.randomUUID();
        CardCreationUpdateRequest request = CardCreationUpdateRequest.builder()
                .deckId(deckId)
                .key("key")
                .value("value")
                .description("desc")
                .build();

        DeckResponse deckResponse = DeckResponse.builder().id(deckId).build();
        List<CardData> cardDataList = List.of();

        when(deckService.getDeckDtoById(deckId)).thenReturn(deckResponse);
        when(cardService.getAllByDeckId(deckId)).thenReturn(cardDataList);
        when(aiClient.sendMessage(CardCreationUpdateRequest.class)).thenReturn(request);

        CardCreationUpdateRequest result = aiService.generateOneCard(request);

        assertThat(result).isEqualTo(request);
        verify(deckService).getDeckDtoById(deckId);
        verify(cardService).getAllByDeckId(deckId);
        verify(aiClient, times(2)).addMessage(any(), any());
        verify(aiClient).sendMessage(CardCreationUpdateRequest.class);
    }

    @Test
    void generateDescriptionDeck_shouldReturnDescriptionDeck() throws Exception {
        UUID deckId = UUID.randomUUID();
        DescriptionDeck descriptionDeck = DescriptionDeck.builder()
                .id(deckId)
                .description("description")
                .build();

        DeckResponse deckResponse = DeckResponse.builder().id(deckId).build();
        List<CardData> cardDataList = List.of();

        when(deckService.getDeckDtoById(deckId)).thenReturn(deckResponse);
        when(cardService.getAllByDeckId(deckId)).thenReturn(cardDataList);
        when(aiClient.sendMessage(DescriptionDeck.class)).thenReturn(descriptionDeck);

        DescriptionDeck result = aiService.generateDescriptionDeck(descriptionDeck);

        assertThat(result).isEqualTo(descriptionDeck);
        verify(deckService).getDeckDtoById(deckId);
        verify(cardService).getAllByDeckId(deckId);
        verify(aiClient, times(2)).addMessage(any(), any());
        verify(aiClient).sendMessage(DescriptionDeck.class);
    }
}
