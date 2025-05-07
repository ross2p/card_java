package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getMyDetails(@AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<User> updateMyDetails(@AuthenticationPrincipal DefaultUserDetails userDetails,
            @RequestBody UserCreationUpdateRequest userCreationUpdateRequest) {
        UUID userId = userDetails.getId();
        System.out.println("updateMyDetails " + userCreationUpdateRequest.toString());
        User updatedUser = userService.updateUser(userId, userCreationUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
