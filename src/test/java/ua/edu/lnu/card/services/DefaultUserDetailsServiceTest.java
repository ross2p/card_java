package ua.edu.lnu.card.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.role.RoleResponse;
import ua.edu.lnu.card.entities.Role;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.mappers.RoleMapper;
import ua.edu.lnu.card.repositories.UserRepository;
import ua.edu.lnu.card.services.impl.DefaultUserDetailsService;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DefaultUserDetailsServiceTest {

    private UserRepository userRepository;
    private RoleMapper roleMapper;
    private DefaultUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleMapper = mock(RoleMapper.class);
        userDetailsService = new DefaultUserDetailsService(userRepository, roleMapper);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // Arrange
        String email = "test@example.com";
        UUID userId = UUID.randomUUID();
        String password = "securePassword";

        Role role = new Role();
        role.setName("ROLE_USER");
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        RoleResponse roleDto = new RoleResponse(null, "ROLE_USER");

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        // Act
        DefaultUserDetails userDetails = (DefaultUserDetails) userDetailsService.loadUserByUsername(email);

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getId()).isEqualTo(userId);
        assertThat(userDetails.getEmail()).isEqualTo(email);
        assertThat(userDetails.getPassword()).isEqualTo(password);
        assertThat(userDetails.getRole()).isEqualTo(roleDto);
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");

        verify(userRepository).findByEmailIgnoreCase(email);
        verify(roleMapper).toDto(role);
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(email);

        verify(userRepository).findByEmailIgnoreCase(email);
        verifyNoInteractions(roleMapper);
    }
}
