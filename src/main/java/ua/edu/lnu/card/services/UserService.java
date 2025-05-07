package ua.edu.lnu.card.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dtos.user.UserResponse;
import ua.edu.lnu.card.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    UserResponse getUserDtoById(UUID userId);

    User createUser(UserCreationUpdateRequest userCreationRequest);

    User updateUser(UUID id, UserCreationUpdateRequest userCreationUpdateRequest);

    void deleteUser(UUID id);

    Page<User> getAllPopularUsers(PageRequest of);
}
