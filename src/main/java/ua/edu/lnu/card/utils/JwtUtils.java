package ua.edu.lnu.card.utils;

import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.auth.Tokens;
import ua.edu.lnu.card.exceptions.exception.HttpError;

public interface JwtUtils {
    String generateAccessToken(DefaultUserDetails user);

    String generateRefreshToken(DefaultUserDetails user);

    boolean validateToken(String token) throws HttpError;

    String getSubject(String token);

    DefaultUserDetails getUserFromToken(String token);

    Tokens generateTokens(DefaultUserDetails userDetails);
}
