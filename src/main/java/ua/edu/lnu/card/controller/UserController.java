package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    public ResponseEntity<UserResponse> getUserById(Long userId) {
        UserResponse userResponse = userService.readById(userId);
        return ResponseEntity.ok(userResponse);
    }



}
