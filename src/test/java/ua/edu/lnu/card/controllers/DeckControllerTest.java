package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.dtos.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DeckResponse;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.entities.Deck;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.services.CardService;
import ua.edu.lnu.card.services.CollaboratorService;
import ua.edu.lnu.card.services.DeckRoleService;
import ua.edu.lnu.card.services.DeckService;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DeckControllerTest {

    @Mock
    private DeckService deckService;
    @Mock
    private CardService cardService;
    @Mock
    private DeckRoleService deckRoleService;
    @Mock
    private CollaboratorService collaboratorService;

    @InjectMocks
    private DeckController deckController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deckController)
                .setCustomArgumentResolvers(new HandlerMethodArgumentResolver() {
                    @Override
                    public boolean supportsParameter(MethodParameter parameter) {
                        return parameter.getParameterAnnotation(AuthenticationPrincipal.class) != null;
                    }

                    @Override
                    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                            NativeWebRequest webRequest,
                            org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
                        return webRequest.getAttribute("userDetails", NativeWebRequest.SCOPE_REQUEST);
                    }
                })
                .build();
    }

    @Test
    void create_shouldReturnDeck() throws Exception {
        UUID userId = UUID.randomUUID();
        DeckCreationUpdateRequest request = DeckCreationUpdateRequest.builder().build();
        Deck deck = new Deck();
        deck.setId(UUID.randomUUID());

        DefaultUserDetails userDetails = mock(DefaultUserDetails.class);
        when(userDetails.getId()).thenReturn(userId);
        when(deckService.createDeck(eq(userId), any(DeckCreationUpdateRequest.class))).thenReturn(deck);

        mockMvc.perform(post("/deck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deck)));

        verify(deckService).createDeck(eq(userId), any(DeckCreationUpdateRequest.class));
    }

    @Test
    void getById_shouldReturnDeckResponse() throws Exception {
        UUID deckId = UUID.randomUUID();
        DeckResponse deckResponse = DeckResponse.builder().id(deckId).build();

        when(deckService.getDeckDtoById(deckId)).thenReturn(deckResponse);

        mockMvc.perform(get("/deck/" + deckId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deckResponse)));

        verify(deckService).getDeckDtoById(deckId);
    }

    @Test
    void getCardByDeckId_shouldReturnCardList() throws Exception {
        UUID deckId = UUID.randomUUID();
        List<CardData> cards = List.of();
        when(cardService.getAllByDeckId(deckId)).thenReturn(cards);

        mockMvc.perform(get("/deck/" + deckId + "/cards"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cards)));

        verify(cardService).getAllByDeckId(deckId);
    }

    @Test
    void getDeckRolesByDeckId_shouldReturnDeckRoleList() throws Exception {
        UUID deckId = UUID.randomUUID();
        List<DeckRole> roles = List.of();
        when(deckRoleService.getDeckRolesByDeckId(deckId)).thenReturn(roles);

        mockMvc.perform(get("/deck/" + deckId + "/deck-roles"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(roles)));

        verify(deckRoleService).getDeckRolesByDeckId(deckId);
    }

    @Test
    void getCollaboratorsByDeckId_shouldReturnCollaboratorList() throws Exception {
        UUID deckId = UUID.randomUUID();
        List<Collaborator> collaborators = List.of();
        when(collaboratorService.getCollaboratorsByDeckId(deckId)).thenReturn(collaborators);

        mockMvc.perform(get("/deck/" + deckId + "/collaborators"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(collaborators)));

        verify(collaboratorService).getCollaboratorsByDeckId(deckId);
    }

    @Test
    void getAllPublic_shouldReturnPageOfDeckResponse() throws Exception {
        Page<DeckResponse> page = new PageImpl<>(List.of(DeckResponse.builder().build()));
        when(deckService.getAllPublicDeck(any(PageRequest.class))).thenReturn(page);

        System.out.println(objectMapper.writeValueAsString(page));
        mockMvc.perform(get("/deck")
                .param("offset", "0")
                .param("pageSize", "10")
                .param("sortBy", "id"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(page)));

        verify(deckService).getAllPublicDeck(any(PageRequest.class));
    }

    @Test
    void update_shouldReturnUpdatedDeck() throws Exception {
        UUID deckId = UUID.randomUUID();
        DeckCreationUpdateRequest request = DeckCreationUpdateRequest.builder().build();
        Deck updatedDeck = new Deck();
        updatedDeck.setId(deckId);

        when(deckService.updateDeck(eq(deckId), any(DeckCreationUpdateRequest.class))).thenReturn(updatedDeck);

        mockMvc.perform(patch("/deck/" + deckId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(updatedDeck)));

        verify(deckService).updateDeck(eq(deckId), any(DeckCreationUpdateRequest.class));
    }
}