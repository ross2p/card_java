package ua.edu.lnu.card.service.impl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.AccessLevel;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.mapper.CollaboratorMapper;
import ua.edu.lnu.card.mapper.DeckMapper;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.repository.DeckRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DeckServiceImplTest {

    @InjectMocks
    private DeckServiceImpl deckService;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private CollaboratorMapper collaboratorMapper;

    @Mock
    private DeckMapper deckMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDeckById_Success() {
        Deck deck = new Deck();
        when(deckRepository.findById(anyLong())).thenReturn(Optional.of(deck));

        Deck result = deckService.getDeckById(1L);

        assertNotNull(result);
        verify(deckRepository).findById(1L);
    }

    @Test
    public void testGetDeckById_NotFound() {
        when(deckRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deckService.getDeckById(1L));
    }

    @Test
    public void testGetPublicDecks() {
        Page<Deck> deckPage = new PageImpl<>(Collections.singletonList(new Deck()));
        when(deckRepository.findByIsprivateFalse(any(PageRequest.class))).thenReturn(deckPage);

        Page<DeckResponse> result = deckService.getPublicDecks(PageRequest.of(0, 10));

        assertNotNull(result);
        verify(deckRepository).findByIsprivateFalse(any(PageRequest.class));
    }

    @Test
    public void testGetDecksByUserId() {
        Page<Deck> deckPage = new PageImpl<>(Collections.singletonList(new Deck()));
        when(deckRepository.findByOwnerId(anyLong(), any(PageRequest.class))).thenReturn(deckPage);

        Page<DeckResponse> result = deckService.getDecksByUserId(1L, PageRequest.of(0, 10));

        assertNotNull(result);
        verify(deckRepository).findByOwnerId(anyLong(), any(PageRequest.class));
    }

    @Test
    public void testGetPublicDecksByUserId() {
        Page<Deck> deckPage = new PageImpl<>(Collections.singletonList(new Deck()));
        when(deckRepository.findByIsprivateFalse(any(PageRequest.class))).thenReturn(deckPage);

        Page<DeckResponse> result = deckService.getPublicDecksByUserId(1L, PageRequest.of(0, 10));

        assertNotNull(result);
        verify(deckRepository).findByIsprivateFalse(any(PageRequest.class));
    }

    @Test
    public void testCreateDeck() {
        DeckCreationUpdateRequest request = DeckCreationUpdateRequest.builder().build();
        Deck deck = new Deck();
        DeckResponse deckResponse = DeckResponse.builder().build();
        Collaborator collaborator = new Collaborator();

        when(deckMapper.toEntity(any(DeckCreationUpdateRequest.class), anyLong())).thenReturn(deck);
        when(collaboratorMapper.toEntity(anyLong(), any(AccessLevel.class))).thenReturn(collaborator);
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);
        when(deckMapper.toDto(any(Deck.class))).thenReturn(deckResponse);

        DeckResponse result = deckService.create(1L, request);

        assertNotNull(result);
        verify(deckRepository).save(any(Deck.class));
    }

    @Test
    public void testAddCollaborator() {
        Long deckId = 1L;
        CollaboratorCreationUpdateRequest request = CollaboratorCreationUpdateRequest.builder().build();
        Deck deck = new Deck();
        Collaborator collaborator = new Collaborator();
        DeckResponse deckResponse = DeckResponse.builder().build();

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
        when(collaboratorMapper.toEntity(any(CollaboratorCreationUpdateRequest.class))).thenReturn(collaborator);
        when(deckRepository.save(any(Deck.class))).thenReturn(deck);
        when(deckMapper.toDto(any(Deck.class))).thenReturn(deckResponse);

        DeckResponse result = deckService.addCollaborator(deckId, request);

        assertNotNull(result);
        verify(deckRepository).save(any(Deck.class));
    }

    @Test
    public void testUpdateDeck() {
        Long deckId = 1L;
        DeckCreationUpdateRequest request = DeckCreationUpdateRequest.builder().build();
        Deck existingDeck = new Deck();
        Deck updatedDeck = new Deck();
        DeckResponse deckResponse = DeckResponse.builder().build();

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(existingDeck));
        when(deckMapper.partialUpdate(any(DeckCreationUpdateRequest.class), any(Deck.class))).thenReturn(updatedDeck);
        when(deckRepository.save(any(Deck.class))).thenReturn(updatedDeck);
        when(deckMapper.toDto(any(Deck.class))).thenReturn(deckResponse);

        DeckResponse result = deckService.update(deckId, request);

        assertNotNull(result);
        verify(deckRepository).save(any(Deck.class));
    }

    @Test
    public void testDeleteDeck() {
        Long deckId = 1L;
        Deck deck = new Deck();
        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));

        deckService.delete(deckId);

        verify(collaboratorRepository).deleteAll(any());
        verify(deckRepository).delete(any(Deck.class));
    }

    @Test
    public void testGetDecksByCollaborator() {
        Page<Deck> deckPage = new PageImpl<>(Collections.singletonList(new Deck()));
        when(deckRepository.findByCollaborators_IdAndOwner_IdOrIsprivateTrueAndOwner_Id(anyLong(), anyLong(), anyLong(), any(PageRequest.class)))
                .thenReturn(deckPage);

        Page<DeckResponse> result = deckService.getDecksByCollaborator(1L, 2L, PageRequest.of(0, 10));

        assertNotNull(result);
        verify(deckRepository).findByCollaborators_IdAndOwner_IdOrIsprivateTrueAndOwner_Id(anyLong(), anyLong(), anyLong(), any(PageRequest.class));
    }
}