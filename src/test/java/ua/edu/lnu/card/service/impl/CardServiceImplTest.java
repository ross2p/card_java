package ua.edu.lnu.card.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardResponse;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.mapper.CardMapper;
import ua.edu.lnu.card.repository.CardRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private CardResponse cardResponse;
    private CardCreationUpdateRequest cardDto;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(1L);
    }

    @Test
    void testReadById_Success() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardMapper.toDto(card)).thenReturn(cardResponse);

        CardResponse result = cardService.readById(1L);

        assertEquals(cardResponse, result);
        verify(cardRepository).findById(1L);
        verify(cardMapper).toDto(card);
    }

    @Test
    void testReadById_NotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardService.readById(1L));
    }

    @Test
    void testGetAllByDeckId_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Card> cards = new PageImpl<>(Collections.singletonList(card));
        when(cardRepository.findAllByDeckId(1L, pageRequest)).thenReturn(cards);
        when(cardMapper.toDto(any(Card.class))).thenReturn(cardResponse);

        Page<CardResponse> result = cardService.getAllByDeckId(1L, pageRequest);

        assertEquals(1, result.getTotalElements());
        verify(cardRepository).findAllByDeckId(1L, pageRequest);
        verify(cardMapper, times(1)).toDto(any(Card.class));
    }

    @Test
    void testCreate_Success() {
        when(cardMapper.toEntity(cardDto, 1L)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardResponse);

        CardResponse result = cardService.create(1L, cardDto);

        assertEquals(cardResponse, result);
        verify(cardMapper).toEntity(cardDto, 1L);
        verify(cardRepository).save(card);
        verify(cardMapper).toDto(card);
    }

    @Test
    void testUpdate_Success() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardMapper.partialUpdate(cardDto, card)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardResponse);

        CardResponse result = cardService.update(1L, cardDto);

        assertEquals(cardResponse, result);
        verify(cardRepository).findById(1L);
        verify(cardMapper).partialUpdate(cardDto, card);
        verify(cardMapper).toDto(card);
    }

    @Test
    void testDelete_Success() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        cardService.delete(1L);

        verify(cardRepository).findById(1L);
        verify(cardRepository).delete(card);
    }

    @Test
    void testDelete_NotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> cardService.delete(1L));
    }
}