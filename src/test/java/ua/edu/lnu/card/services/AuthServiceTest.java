package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.auth.Tokens;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.exception.exception.client.BadRequest;
import ua.edu.lnu.card.mappers.UserMapper;
import ua.edu.lnu.card.service.impl.AuthServiceImpl;
import ua.edu.lnu.card.utils.JwtUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userService, authenticationManager, jwtUtils, userMapper);
    }

    @Test
    void register_shouldReturnTokens() {
        UserCreationUpdateRequest req = UserCreationUpdateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();
        User user = new User();
        Tokens tokens = new Tokens("access", "refresh");

        when(userService.createUser(req)).thenReturn(user);
        when(userMapper.toPayload(user)).thenReturn(new DefaultUserDetails());
        when(jwtUtils.generateTokens(any(DefaultUserDetails.class))).thenReturn(tokens);

        Tokens result = authService.register(req);

        assertThat(result).isEqualTo(tokens);
        verify(userService).createUser(req);
        verify(jwtUtils).generateTokens(any(DefaultUserDetails.class));
    }

    @Test
    void login_shouldReturnTokens_whenCredentialsValid() {
        String email = "test@example.com";
        String password = "password";
        Tokens tokens = new Tokens("access", "refresh");
        Authentication authentication = mock(Authentication.class);
        DefaultUserDetails userDetails = new DefaultUserDetails();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateTokens(userDetails)).thenReturn(tokens);

        Tokens result = authService.login(email, password);

        assertThat(result).isEqualTo(tokens);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateTokens(userDetails);
    }

    @Test
    void login_shouldThrowBadRequest_whenCredentialsInvalid() {
        String email = "test@example.com";
        String password = "wrong";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid"));

        assertThatThrownBy(() -> authService.login(email, password))
                .isInstanceOf(BadRequest.class)
                .hasMessageContaining("Invalid email or password");
    }

    @Test
    void refreshToken_shouldReturnNewTokens_whenValid() {
        String refreshToken = "refresh";
        String accessToken = "access";
        DefaultUserDetails userDetails = new DefaultUserDetails();
        User user = new User();

        when(jwtUtils.validateToken(refreshToken)).thenReturn(true);
        when(jwtUtils.getUserFromToken(refreshToken)).thenReturn(userDetails);
        when(userService.getUserById(userDetails.getId())).thenReturn(user);
        when(userMapper.toPayload(user)).thenReturn(userDetails);
        when(jwtUtils.generateAccessToken(userDetails)).thenReturn(accessToken);

        Tokens result = authService.refreshToken(refreshToken);

        assertThat(result.accessToken()).isEqualTo(accessToken);
        assertThat(result.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void refreshToken_shouldThrowBadCredentials_whenInvalid() {
        String refreshToken = "invalid";
        when(jwtUtils.validateToken(refreshToken)).thenReturn(false);

        assertThatThrownBy(() -> authService.refreshToken(refreshToken))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Invalid refresh token");
    }
}
