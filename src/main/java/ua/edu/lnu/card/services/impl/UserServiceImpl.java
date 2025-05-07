package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dtos.user.UserResponse;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.exceptions.exception.client.NotFound;
import ua.edu.lnu.card.mappers.UserMapper;
import ua.edu.lnu.card.repositories.RoleRepository;
import ua.edu.lnu.card.repositories.UserRepository;
import ua.edu.lnu.card.services.UserService;
import ua.edu.lnu.card.utils.Constants;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFound("User with id %s not found".formatted(id)));
    }

    @Override
    public UserResponse getUserDtoById(UUID userId) {
        return userMapper.toDto(this.getUserById(userId));
    }

    @Override
    public User createUser(UserCreationUpdateRequest userCreationRequest) {
        User newUser = userMapper.toEntity(userCreationRequest);
        if (newUser.getRole() == null) {
            newUser.setRole(roleRepository.findByName(Constants.DEFAULT_ROLE));
        }
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UUID userId, UserCreationUpdateRequest userCreationUpdateRequest) {
        User user = this.getUserById(userId);
        user = userMapper.partialUpdate(userCreationUpdateRequest, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getAllPopularUsers(PageRequest of) {
        return userRepository.findAll(of);
    }
}
