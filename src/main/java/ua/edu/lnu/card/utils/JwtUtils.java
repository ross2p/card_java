package ua.edu.lnu.card.utils;

public interface JwtUtils {
    String generateToken(String username);

    boolean validateToken(String token);

    String getSubject(String token);
}
