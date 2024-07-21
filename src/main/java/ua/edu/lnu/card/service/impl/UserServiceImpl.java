package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.exception.AlreadyExistsException;
import ua.edu.lnu.card.mapper.UserMapper;
import ua.edu.lnu.card.repository.RoleRepository;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(id)));
    }
    @Override
    public UserResponse getById(Long id) {
        User user = getUserById(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponse create(UserCreationUpdateRequest userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException("User with email address '%s' already exists.".formatted(userDto.getEmail()));
        }
        User user = userMapper.toEntity(userDto);

        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserResponse update(Long id, UserCreationUpdateRequest userCreationUpdateRequest) {
        User user = getUserById(id);
        User updatedUser = userMapper.partialUpdate(userCreationUpdateRequest, user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        updatedUser = userRepository.save(updatedUser);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);        //todo: add column status (blocked, deleted, active)
    }

    @Override
    public Page<UserResponse> getUsersPage(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(userMapper::toDto);
    }

    @Override
    public UserResponse updateRole(Long userId, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with id '%d' not found.".formatted(roleId)));

        User user = getUserById(userId);
        user.setRole(role);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
}
