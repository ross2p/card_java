package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.service.UserService;


import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getMyDetails(@AuthenticationPrincipal DefaultUserDetails userDetails) {
        System.out.println("userDetails" + userDetails);
        UUID userId = userDetails.getId();
        UserResponse user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateMyDetails(@AuthenticationPrincipal DefaultUserDetails userDetails,
                                                        @RequestBody UserCreationUpdateRequest userCreationUpdateRequest) {
        UUID userId = userDetails.getId();
        System.out.println("updateMyDetails " + userCreationUpdateRequest.toString());
        UserResponse updatedUser = userService.update(userId, userCreationUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
