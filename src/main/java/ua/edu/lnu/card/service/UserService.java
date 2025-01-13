package ua.edu.lnu.card.service;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    UserResponse getById(UUID userId);

    User create(UserCreationUpdateRequest userCreationRequest);

    UserResponse update(UUID id, UserCreationUpdateRequest userCreationUpdateRequest);

    void delete(UUID id);
}
