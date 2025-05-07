package ua.edu.lnu.card.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dtos.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DeckResponse;
import ua.edu.lnu.card.entities.Deck;
import ua.edu.lnu.card.exceptions.exception.server.NotImplemented;
import ua.edu.lnu.card.mappers.DeckMapper;
import ua.edu.lnu.card.repositories.DeckRepository;
import ua.edu.lnu.card.services.impl.DeckServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeckServiceImplTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private DeckMapper deckMapper;

    @Mock
    private DeckRoleService deckRoleService;

    @Mock
    private CollaboratorService collaboratorService;

    @InjectMocks
    private DeckServiceImpl deckService;

    private UUID deckId;
    private Deck deck;
    private DeckCreationUpdateRequest deckCreationUpdateRequest;
    private DeckResponse deckResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        deckId = UUID.randomUUID();
        deck = new Deck();
        deck.setId(deckId);

        deckCreationUpdateRequest = DeckCreationUpdateRequest.builder().build();
        deckResponse = DeckResponse.builder().id(deckId).build();

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
    }

    @Test
    void testGetDeckById() {
        Deck result = deckService.getDeckById(deckId);
        assertNotNull(result);
        assertEquals(deckId, result.getId());
    }

    @Test
    void testGetDeckById_NotFound() {
        when(deckRepository.findById(deckId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deckService.getDeckById(deckId));
        assertEquals("Deck with id %s not found".formatted(deckId), exception.getMessage());
    }

    @Test
    void testGetAllPublicDeck() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Deck> deckPage = mock(Page.class);
        when(deckRepository.findAll(any(), eq(pageRequest))).thenReturn(deckPage);
        when(deckPage.map(any())).thenReturn(mock(Page.class));

        Page<DeckResponse> result = deckService.getAllPublicDeck(pageRequest);
        assertNotNull(result);
    }

    @Test
    void testCreateDeck() {
        when(deckRepository.saveAndFlush(any())).thenReturn(deck);
        when(deckMapper.toDto(any())).thenReturn(deckResponse);

        Deck savedDeck = deckService.createDeck(UUID.randomUUID(), deckCreationUpdateRequest);

        assertNotNull(savedDeck);
        verify(deckRoleService).createDefaultDeckRole(eq(deckId));
        verify(collaboratorService).createOwnerCollaborator(eq(deckId), any());
    }

    @Test
    void testUpdateDeck() {
        Deck updatedDeck = new Deck();
        when(deckMapper.partialUpdate(eq(deckCreationUpdateRequest), eq(deck))).thenReturn(updatedDeck);
        when(deckRepository.save(eq(updatedDeck))).thenReturn(updatedDeck);

        Deck result = deckService.updateDeck(deckId, deckCreationUpdateRequest);
        assertNotNull(result);
        verify(deckRepository).save(eq(updatedDeck));
    }

    @Test
    void testDeleteDeck() {
        NotImplemented exception = assertThrows(NotImplemented.class, () -> deckService.deleteDeck(deckId));
        assertEquals("Not implemented yet", exception.getMessage());
    }

    @Test
    void testGetDeckDtoById() {
        when(deckMapper.toDto(eq(deck))).thenReturn(deckResponse);

        DeckResponse result = deckService.getDeckDtoById(deckId);
        assertNotNull(result);
    }
}
