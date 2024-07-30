package ua.edu.lnu.card.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DefaultUserDetailsServiceTest {

    @InjectMocks
    private DefaultUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword("password");

        // Assuming Role constructor takes Long id and String name
        Role role = new Role(1L, "ROLE_USER");
        user.setRole(role);

        // Mock the repository to return the user
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Call the method under test
        UserDetails result = userDetailsService.loadUserByUsername(email);

        // Verify the results
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(1, result.getAuthorities().size()); // Expecting one authority
        assertEquals("ROLE_USER", result.getAuthorities().iterator().next().getAuthority());
        verify(userRepository).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "test@example.com";

        // Mock the repository to return empty
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Call the method under test and verify the exception
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email),
                "Expected loadUserByUsername() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("User with email address 'test@example.com' not found"));
        verify(userRepository).findByEmail(email);
    }
}