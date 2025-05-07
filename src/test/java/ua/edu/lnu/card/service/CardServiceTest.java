package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.exception.exception.client.NotFound;
import ua.edu.lnu.card.mapper.CardMapper;
import ua.edu.lnu.card.repository.CardRepository;
import ua.edu.lnu.card.service.impl.CardServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = new CardServiceImpl(cardMapper, cardRepository);
    }

    @Test
    void getCardById_shouldReturnCardData_whenFound() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        Card card = new Card();
        card.setId(cardId);
        CardData expectedCardData = new CardData(cardId, "key", "value", "description", new Date(), new Date());

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardMapper.toDto(card)).thenReturn(expectedCardData);

        // Act
        CardData result = cardService.getCardDataById(cardId);

        // Assert
        assertThat(result).isEqualTo(expectedCardData);
        verify(cardRepository).findById(cardId);
        verify(cardMapper).toDto(card);
    }

    @Test
    void getCardById_shouldThrowException_whenNotFound() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> cardService.getCardDataById(cardId))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Card with id");

        verify(cardRepository).findById(cardId);
        verifyNoInteractions(cardMapper);
    }

    @Test
    void getAllByDeckId_shouldReturnListOfCardData() {
        // Arrange
        UUID deckId = UUID.randomUUID();

        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Card card1 = new Card();
        card1.setId(id1);
        Card card2 = new Card();
        card2.setId(id2);
        List<Card> cards = List.of(card1, card2);

        CardData cardData1 = new CardData(id1, "key1", "value1", "description1", new Date(), new Date());
        CardData cardData2 = new CardData(id2, "key2", "value2", "description2", new Date(), new Date());
        List<CardData> expectedCardDataList = List.of(cardData1, cardData2);

        when(cardRepository.findByDeck_IdOrderByCreatedAtAsc(deckId)).thenReturn(cards);
        when(cardMapper.toDto(card1)).thenReturn(cardData1);
        when(cardMapper.toDto(card2)).thenReturn(cardData2);

        // Act
        List<CardData> result = cardService.getAllByDeckId(deckId);

        // Assert
        assertThat(result).isEqualTo(expectedCardDataList);
        verify(cardRepository).findByDeck_IdOrderByCreatedAtAsc(deckId);
        verify(cardMapper, times(2)).toDto(any(Card.class));
    }

    @Test
    void create_shouldCreateAndReturnCard() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        CardCreationUpdateRequest request = new CardCreationUpdateRequest(deckId, "key", "value", "description");
        Card card = new Card();
        Card expectedCard = new Card();
        expectedCard.setId(UUID.randomUUID());

        when(cardMapper.toEntity(request)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(expectedCard);

        // Act
        Card result = cardService.create(request);

        // Assert
        assertThat(result).isEqualTo(expectedCard);
        verify(cardMapper).toEntity(request);
        verify(cardRepository).save(card);
    }

    @Test
    void update_shouldUpdateAndReturnCardData() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        UUID deckId = UUID.randomUUID();
        CardCreationUpdateRequest request = new CardCreationUpdateRequest(deckId, "updated key", "updated value",
                "updated description");

        Card existingCard = new Card();
        existingCard.setId(cardId);

        Card updatedCard = new Card();
        updatedCard.setId(cardId);

        CardData expectedCardData = new CardData(cardId, "updated key", "updated value", "updated description",
                new Date(), new Date());

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardMapper.partialUpdate(request, existingCard)).thenReturn(updatedCard);
        when(cardRepository.save(updatedCard)).thenReturn(updatedCard);
        when(cardMapper.toDto(updatedCard)).thenReturn(expectedCardData);

        // Act
        CardData result = cardService.update(cardId, request);

        // Assert
        assertThat(result).isEqualTo(expectedCardData);
        verify(cardRepository).findById(cardId);
        verify(cardMapper).partialUpdate(request, existingCard);
        verify(cardRepository).save(updatedCard);
        verify(cardMapper).toDto(updatedCard);
    }

    @Test
    void update_shouldThrowException_whenCardNotFound() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        UUID deckId = UUID.randomUUID();
        CardCreationUpdateRequest request = new CardCreationUpdateRequest(deckId, "key", "value", "description");

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> cardService.update(cardId, request))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Card with id");

        verify(cardRepository).findById(cardId);
        verifyNoInteractions(cardMapper);
    }

    @Test
    void delete_shouldDeleteCard() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        when(cardRepository.existsById(cardId)).thenReturn(true);

        // Act
        cardService.delete(cardId);

        // Assert
        verify(cardRepository).existsById(cardId);
        verify(cardRepository).deleteById(cardId);
    }

    @Test
    void delete_shouldThrowException_whenCardNotFound() {
        // Arrange
        UUID cardId = UUID.randomUUID();
        when(cardRepository.existsById(cardId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> cardService.delete(cardId))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Card with id");

        verify(cardRepository).existsById(cardId);
        verifyNoMoreInteractions(cardRepository);
    }
}