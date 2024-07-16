package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/sing-up")
    public ResponseEntity<UserResponse> createUser(UserCreationUpdateRequest userCreationRequest) {
        UserResponse userResponse = userService.create(userCreationRequest);
        return ResponseEntity.ok(userResponse);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(Long userId,  UserCreationUpdateRequest userCreationUpdateRequest){
        UserResponse userResponse = userService.update(userId, userCreationUpdateRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
