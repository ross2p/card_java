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
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.exception.AlreadyExistsException;
import ua.edu.lnu.card.mapper.UserMapper;
import ua.edu.lnu.card.repository.RoleRepository;
import ua.edu.lnu.card.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getById_ShouldReturnUserResponse_WhenUserExists() {
        User user = new User();
        UserResponse userResponse = UserResponse.builder().build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        UserResponse result = userService.getById(1L);

        assertEquals(userResponse, result);
    }

    @Test
    void create_ShouldReturnUserResponse_WhenUserIsCreated() {
        UserCreationUpdateRequest request = UserCreationUpdateRequest
                .builder().email("test@example.com")
                .build();
        User user = new User();
        UserResponse userResponse = UserResponse.builder().build();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(UserCreationUpdateRequest.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        UserResponse result = userService.create(request);

        assertEquals(userResponse, result);
    }

    @Test
    void create_ShouldThrowException_WhenEmailAlreadyExists() {
        UserCreationUpdateRequest request = UserCreationUpdateRequest
                .builder().email("test@example.com")
                .build();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.create(request));
    }

    @Test
    void update_ShouldReturnUserResponse_WhenUserIsUpdated() {
        UserCreationUpdateRequest request = UserCreationUpdateRequest.builder().build();
        User user = new User();
        user.setPassword("oldPassword");
        UserResponse userResponse = UserResponse.builder().build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.partialUpdate(any(UserCreationUpdateRequest.class), any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        UserResponse result = userService.update(1L, request);

        assertEquals(userResponse, result);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void delete_ShouldDeleteUser_WhenUserExists() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getUsersPage_ShouldReturnPageOfUserResponses() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        User user = new User();
        UserResponse userResponse = UserResponse.builder().build();
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        Page<UserResponse> result = userService.getUsersPage(pageRequest);

        assertEquals(1, result.getTotalElements());
        assertEquals(userResponse, result.getContent().get(0));
    }

    @Test
    void updateRole_ShouldReturnUserResponse_WhenRoleIsUpdated() {
        User user = new User();
        Role role = new Role();
        UserResponse userResponse = UserResponse.builder().build();
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userResponse);

        UserResponse result = userService.updateRole(1L, 1L);

        assertEquals(userResponse, result);
        assertEquals(role, user.getRole());
    }

    @Test
    void updateRole_ShouldThrowException_WhenRoleDoesNotExist() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateRole(1L, 1L));
    }
}