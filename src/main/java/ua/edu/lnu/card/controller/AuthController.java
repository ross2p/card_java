package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.auth.Token;
import ua.edu.lnu.card.dto.auth.Tokens;
import ua.edu.lnu.card.dto.auth.LoginAuthentication;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.exceptions.exception.HttpError;
import ua.edu.lnu.card.exceptions.exception.client.BadRequest;
import ua.edu.lnu.card.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Tokens> register(@RequestBody UserCreationUpdateRequest userCreationRequest) {
        Tokens tokens = authService.register(userCreationRequest);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody LoginAuthentication loginRequest) throws BadRequest {
        Tokens tokens = authService.login(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Tokens> refresh(@RequestBody Token token) throws HttpError {
        System.out.println("refresh token: " + token.refreshToken());
        Tokens tokens = authService.refreshToken(token.refreshToken());

        return ResponseEntity.ok(tokens);
    }
}