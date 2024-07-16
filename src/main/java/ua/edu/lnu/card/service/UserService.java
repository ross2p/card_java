package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.user.AdminCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;

public interface UserService {
    UserResponse getById(Long userId);


    UserResponse create(UserCreationUpdateRequest userCreationRequest);

    UserResponse create(AdminCreationUpdateRequest userCreationRequest);


    UserResponse update(Long id, UserCreationUpdateRequest userCreationUpdateRequest);

    void delete(Long id);
}
