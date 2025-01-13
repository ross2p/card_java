package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.auth.Tokens;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.exception.exception.HttpError;
import ua.edu.lnu.card.exception.exception.client.BadRequest;

public interface AuthService {
    Tokens register(UserCreationUpdateRequest userCreationUpdateRequest);

    Tokens login(String email, String password);

    Tokens refreshToken(String refreshToken);
}
