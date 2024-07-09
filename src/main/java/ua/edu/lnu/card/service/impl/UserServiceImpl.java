package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.mapper.UserMapper;
import ua.edu.lnu.card.repository.UserRepository;
import ua.edu.lnu.card.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse readById(Long userId) {
        User user = getUserById(userId);
        return UserMapper.toRespons(user);
    }


    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(id)));
    }

}
