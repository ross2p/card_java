package ua.edu.lnu.card.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;
import ua.edu.lnu.card.entities.DeckRating;
import ua.edu.lnu.card.exceptions.exception.client.NotFound;
import ua.edu.lnu.card.mappers.DeckRatingMapper;
import ua.edu.lnu.card.repositories.DeckRatingRepository;
import ua.edu.lnu.card.services.impl.DeckRatingServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeckRatingServiceTest {

    @Mock
    private DeckRatingRepository deckRatingRepository;

    @Mock
    private DeckRatingMapper deckRatingMapper;

    private DeckRatingService deckRatingService;

    @BeforeEach
    void setUp() {
        deckRatingService = new DeckRatingServiceImpl(deckRatingRepository, deckRatingMapper);
    }

    @Test
    void save_shouldSaveAndReturnDeckRatingResponse() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID deckId = UUID.randomUUID();
        Double rating = 4.5;
        DeckRatingCreationRequest request = new DeckRatingCreationRequest(rating, deckId);
        DeckRating deckRating = new DeckRating();
        DeckRatingResponse expectedResponse = DeckRatingResponse.builder().id(deckId).build();

        when(deckRatingMapper.toEntity(rating, deckId, userId)).thenReturn(deckRating);
        when(deckRatingRepository.save(deckRating)).thenReturn(deckRating);
        when(deckRatingMapper.toDto(deckRating)).thenReturn(expectedResponse);

        // Act
        DeckRatingResponse result = deckRatingService.save(request, userId);

        // Assert
        assertThat(result).isEqualTo(expectedResponse);
        verify(deckRatingMapper).toEntity(rating, deckId, userId);
        verify(deckRatingRepository).save(deckRating);
        verify(deckRatingMapper).toDto(deckRating);
    }

    @Test
    void getDeckRatingById_shouldReturnDeckRatingResponse_whenFound() {
        // Arrange
        UUID ratingId = UUID.randomUUID();
        DeckRating deckRating = new DeckRating();
        DeckRatingResponse expectedResponse = DeckRatingResponse.builder()
                .id(ratingId)
                .build();

        when(deckRatingRepository.findById(ratingId)).thenReturn(Optional.of(deckRating));
        when(deckRatingMapper.toDto(deckRating)).thenReturn(expectedResponse);

        // Act
        DeckRatingResponse result = deckRatingService.getDeckRatingById(ratingId);

        // Assert
        assertThat(result).isEqualTo(expectedResponse);
        verify(deckRatingRepository).findById(ratingId);
        verify(deckRatingMapper).toDto(deckRating);
    }

    @Test
    void getDeckRatingById_shouldThrowException_whenNotFound() {
        // Arrange
        UUID ratingId = UUID.randomUUID();
        when(deckRatingRepository.findById(ratingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deckRatingService.getDeckRatingById(ratingId))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("Deck rating with id");

        verify(deckRatingRepository).findById(ratingId);
        verifyNoInteractions(deckRatingMapper);
    }
}
