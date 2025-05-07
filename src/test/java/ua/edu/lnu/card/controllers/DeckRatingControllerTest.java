package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.edu.lnu.card.controllers.DeckRatingController;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;
import ua.edu.lnu.card.services.DeckRatingService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeckRatingControllerTest {

        @Mock
        private DeckRatingService deckRatingService;

        @InjectMocks
        private DeckRatingController deckRatingController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(deckRatingController)
                                .setCustomArgumentResolvers(
                                                new org.springframework.web.method.support.HandlerMethodArgumentResolver() {
                                                        @Override
                                                        public boolean supportsParameter(
                                                                        org.springframework.core.MethodParameter parameter) {
                                                                return parameter.getParameterAnnotation(
                                                                                org.springframework.security.core.annotation.AuthenticationPrincipal.class) != null;
                                                        }

                                                        @Override
                                                        public Object resolveArgument(
                                                                        org.springframework.core.MethodParameter parameter,
                                                                        org.springframework.web.method.support.ModelAndViewContainer mavContainer,
                                                                        org.springframework.web.context.request.NativeWebRequest webRequest,
                                                                        org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
                                                                return webRequest.getAttribute("userDetails",
                                                                                org.springframework.web.context.request.NativeWebRequest.SCOPE_REQUEST);
                                                        }
                                                })
                                .build();
        }

        @Test
        void create_shouldReturnDeckRatingResponse() throws Exception {
                UUID userId = UUID.randomUUID();
                DeckRatingCreationRequest request = new DeckRatingCreationRequest(4.5, UUID.randomUUID());
                DeckRatingResponse response = DeckRatingResponse.builder()
                                .id(UUID.randomUUID())
                                .build();

                DefaultUserDetails userDetails = mock(DefaultUserDetails.class);
                when(userDetails.getId()).thenReturn(userId);
                when(deckRatingService.save(any(DeckRatingCreationRequest.class), eq(userId))).thenReturn(response);

                mockMvc.perform(post("/rating")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .requestAttr("userDetails", userDetails))
                                .andExpect(status().isOk())
                                .andExpect(content().string(objectMapper.writeValueAsString(response)));

                verify(deckRatingService).save(any(DeckRatingCreationRequest.class), eq(userId));
        }

        @Test
        void getByDeckId_shouldReturnDeckRatingResponse() throws Exception {
                UUID id = UUID.randomUUID();
                DeckRatingResponse response = DeckRatingResponse.builder()
                                .id(id)
                                .build();

                when(deckRatingService.getDeckRatingById(id)).thenReturn(response);

                mockMvc.perform(get("/rating/" + id))
                                .andExpect(status().isOk())
                                .andExpect(content().string(objectMapper.writeValueAsString(response)));

                verify(deckRatingService).getDeckRatingById(id);
        }
}
