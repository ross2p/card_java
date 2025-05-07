package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.auth.Tokens;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.exceptions.exception.client.BadRequest;
import ua.edu.lnu.card.mappers.UserMapper;
import ua.edu.lnu.card.services.AuthService;
import ua.edu.lnu.card.services.UserService;
import ua.edu.lnu.card.utils.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Override
    public Tokens register(UserCreationUpdateRequest userCreationUpdateRequest) {
        User user = userService.createUser(userCreationUpdateRequest);
        return jwtUtils.generateTokens(userMapper.toPayload(user));
    }

    @Override
    public Tokens login(String email, String password) throws BadRequest {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();

            return jwtUtils.generateTokens(userDetails);
        } catch (AuthenticationException e) {
            throw new BadRequest("Invalid email or password supplied");
        }
    }

    @Override
    public Tokens refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        DefaultUserDetails user = jwtUtils.getUserFromToken(refreshToken);
        System.out.println("user: " + user);

        User existingUser = userService.getUserById(user.getId());

        String accessToken = jwtUtils.generateAccessToken(userMapper.toPayload(existingUser));

        return new Tokens(accessToken, refreshToken);
    }

}
