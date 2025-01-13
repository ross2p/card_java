package ua.edu.lnu.card.utils;

import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.auth.Tokens;
import ua.edu.lnu.card.exception.exception.HttpError;

public interface JwtUtils {
    String generateAccessToken(DefaultUserDetails user);

    String generateRefreshToken(DefaultUserDetails user);

    boolean validateToken(String token) throws HttpError;

    String getSubject(String token);

    DefaultUserDetails getUserFromToken(String token);

    Tokens generateTokens(DefaultUserDetails userDetails);
}
