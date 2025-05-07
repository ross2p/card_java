package ua.edu.lnu.card.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.auth.Tokens;
import ua.edu.lnu.card.dto.role.RoleResponse;
import ua.edu.lnu.card.exception.exception.client.BadRequest;
import ua.edu.lnu.card.exception.exception.client.Forbidden;
import ua.edu.lnu.card.exception.exception.client.Unauthorized;
import io.jsonwebtoken.security.SignatureException;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

@Slf4j
@Component
public class BaseJwtUtils implements JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refresh-token-lifetime}")
    private Duration refreshTime;

    @Value("${jwt.access-token-lifetime}")
    private Duration accessTime;

    private String generateToken(DefaultUserDetails user, Duration lifetime) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + lifetime.toMillis());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("card")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .claim("id", user.getId().toString())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String generateRefreshToken(DefaultUserDetails user) {
        return generateToken(user, refreshTime);
    }

    @Override
    public String generateAccessToken(DefaultUserDetails user) {
        return generateToken(user, accessTime);
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new BadRequest("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new Unauthorized("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new BadRequest("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadRequest("JWT claims string is empty: " + e.getMessage());
        } catch (SignatureException e) {
            throw new Forbidden("JWT token signature validation failed: " + e.getMessage());
        }
    }

    public Tokens generateTokens(DefaultUserDetails userDetails) {
        String accessToken = generateAccessToken(userDetails);
        String refreshToken = generateRefreshToken(userDetails);
        return new Tokens(accessToken, refreshToken);
    }

    @Override
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public DefaultUserDetails getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        UUID id = UUID.fromString(claims.get("id", String.class));
        String email = claims.getSubject();
        System.out.println(email);
        System.out.println(claims.get("role", Map.class));
        RoleResponse role = new ObjectMapper().convertValue(claims.get("role", Map.class), RoleResponse.class);
        System.out.println(role);
        DefaultUserDetails defaultUserDetails = new DefaultUserDetails();
        defaultUserDetails.setId(id);
        defaultUserDetails.setEmail(email);
        defaultUserDetails.setRole(role);
        System.out.println(defaultUserDetails);

        return defaultUserDetails;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
