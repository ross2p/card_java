package ua.edu.lnu.card.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.edu.lnu.card.dtos.auth.Token;
import ua.edu.lnu.card.dtos.auth.Tokens;
// import ua.edu.lnu.card.controllers.AuthController;
import ua.edu.lnu.card.dtos.auth.LoginAuthentication;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.services.AuthService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void register_shouldReturnTokens() throws Exception {
        UserCreationUpdateRequest request = UserCreationUpdateRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("pass")
                .build();
        Tokens tokens = new Tokens("access", "refresh");

        when(authService.register(any(UserCreationUpdateRequest.class))).thenReturn(tokens);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tokens)));

        verify(authService).register(any(UserCreationUpdateRequest.class));
    }

    @Test
    void login_shouldReturnTokens() throws Exception {
        LoginAuthentication loginRequest = new LoginAuthentication("test@example.com", "pass");
        Tokens tokens = new Tokens("access", "refresh");

        when(authService.login(eq("test@example.com"), eq("pass"))).thenReturn(tokens);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tokens)));

        verify(authService).login(eq("test@example.com"), eq("pass"));
    }

    @Test
    void refresh_shouldReturnTokens() throws Exception {
        Token token = new Token("refresh");
        Tokens tokens = new Tokens("access", "refresh");

        when(authService.refreshToken(eq("refresh"))).thenReturn(tokens);

        mockMvc.perform(post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(token)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tokens)));

        verify(authService).refreshToken(eq("refresh"));
    }
}
