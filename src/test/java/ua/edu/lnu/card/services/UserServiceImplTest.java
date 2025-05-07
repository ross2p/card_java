package ua.edu.lnu.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dtos.user.UserResponse;
import ua.edu.lnu.card.entities.Role;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.exception.exception.client.NotFound;
import ua.edu.lnu.card.mappers.UserMapper;
import ua.edu.lnu.card.repository.RoleRepository;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.impl.UserServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, roleRepository, userMapper);
    }

    @Test
    void getUserById_shouldReturnUser_whenFound() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserById_shouldThrow_whenNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(NotFound.class)
                .hasMessageContaining("User with id");
    }

    @Test
    void getUserDtoById_shouldReturnMappedDto() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        UserResponse userResponse = new UserResponse();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserDtoById(userId);

        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    void createUser_shouldSetDefaultRoleAndSave() {
        UserCreationUpdateRequest request = UserCreationUpdateRequest.builder().build();
        Role role = new Role();
        User user = new User();
        user.setRole(role);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(request);

        assertThat(result.getRole()).isEqualTo(role);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_shouldUpdateAndSave() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        User updatedUser = new User();
        UserCreationUpdateRequest request = UserCreationUpdateRequest.builder().build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.partialUpdate(request, user)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, request);

        assertThat(result).isEqualTo(updatedUser);
        verify(userRepository).save(updatedUser);
    }

    @Test
    void deleteUser_shouldCallRepositoryDeleteById() {
        UUID id = UUID.randomUUID();

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void getAllPopularUsers_shouldReturnPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(new User()));
        when(userRepository.findAll(pageRequest)).thenReturn(page);

        Page<User> result = userService.getAllPopularUsers(pageRequest);

        assertThat(result.getContent()).hasSize(1);
    }
}
