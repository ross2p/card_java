package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.user.UserResponse;

public interface UserService {
    UserResponse readById(Long userId);
}
