package ua.edu.lnu.card.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    UserResponse getUserDtoById(UUID userId);

    User createUser(UserCreationUpdateRequest userCreationRequest);

    User updateUser(UUID id, UserCreationUpdateRequest userCreationUpdateRequest);

    void deleteUser(UUID id);

    Page<User> getAllPopularUsers(PageRequest of);
}
