package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.auth.JwtAuthenticationResponse;
import ua.edu.lnu.card.dto.auth.LoginAuthentication;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.service.UserService;
import ua.edu.lnu.card.utils.JwtUtils;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreationUpdateRequest userCreationRequest, UriComponentsBuilder ucb) {
        System.out.println("register "+userCreationRequest.toString());
        UserResponse userResponse = userService.create(userCreationRequest);

        URI location = ucb
                .path("/api/users/{userId}")
                .buildAndExpand(userResponse.id())
                .toUri();
        return ResponseEntity.created(location).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginAuthentication loginRequest) {
        Objects.requireNonNull(loginRequest, "Request user must not be null");
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}