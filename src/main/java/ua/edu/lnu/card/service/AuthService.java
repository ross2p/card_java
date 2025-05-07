package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.auth.Tokens;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;

public interface AuthService {
    Tokens register(UserCreationUpdateRequest userCreationUpdateRequest);

    Tokens login(String email, String password);

    Tokens refreshToken(String refreshToken);
}
