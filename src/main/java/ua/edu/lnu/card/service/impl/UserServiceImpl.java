package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.exception.AlreadyExistsException;
import ua.edu.lnu.card.mapper.UserMapper;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.UserService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(id)));
    }
    @Override
    public UserResponse getById(Long id) {
        User user = getUserById(id);
        return userMapper.toDto(user);
    }

//    @Override
//    public UserResponse create(UserCreationUpdateRequest userCreationRequest) {
//        User user = userMapper.toEntity(userCreationRequest);
//        user.setCreatedAt(Instant.now());
//        user.setUpdatedBy(user.getEmail());
//        user = userRepository.save(user);
//        return userMapper.toDto(user);
//    }
@Override
public UserResponse create(UserCreationUpdateRequest userDto) {
    if (userRepository.existsByEmail(userDto.email())) {
        throw new AlreadyExistsException("User with email address '%s' already exists.".formatted(userDto.email()));
    }
    User user = userMapper.toEntity(userDto);

    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user = userRepository.save(user);

    return userMapper.toDto(user);
}
    @Override
    public UserResponse update(Long id, UserCreationUpdateRequest userCreationUpdateRequest) {
        User user = getUserById(id);
        User updatedUser = userMapper.partialUpdate(userCreationUpdateRequest, user);
        updatedUser.setUpdatedOn(Instant.now());
        updatedUser.setUpdatedBy(user.getEmail());
        updatedUser = userRepository.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}