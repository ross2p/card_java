package ua.edu.lnu.card.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
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
    void getMyDetails_shouldReturnUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        DefaultUserDetails userDetails = mock(DefaultUserDetails.class);
        when(userDetails.getId()).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/user")
                .principal(() -> userId.toString())
                .requestAttr("userDetails", userDetails))
                .andExpect(status().isOk());
        verify(userService).getUserById(userId);
    }

    @Test
    void updateMyDetails_shouldReturnUpdatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserCreationUpdateRequest req = UserCreationUpdateRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("pass")
                .build();
        User updatedUser = new User();
        updatedUser.setId(userId);

        DefaultUserDetails userDetails = mock(DefaultUserDetails.class);
        when(userDetails.getId()).thenReturn(userId);
        when(userService.updateUser(eq(userId), any(UserCreationUpdateRequest.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .requestAttr("userDetails", userDetails))
                .andExpect(status().isOk());
        verify(userService).updateUser(eq(userId), any(UserCreationUpdateRequest.class));
    }

    @Test
    void deleteMyAccount_shouldReturnNoContent() throws Exception {
        UUID userId = UUID.randomUUID();
        DefaultUserDetails userDetails = mock(DefaultUserDetails.class);
        when(userDetails.getId()).thenReturn(userId);

        mockMvc.perform(delete("/user")
                .requestAttr("userDetails", userDetails))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(userId);
    }
}
