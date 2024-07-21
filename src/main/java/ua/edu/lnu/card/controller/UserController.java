package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getById(userId);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsers(@RequestParam(value = "offset", required = false) Integer offset,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "sortBy", required = false) String sortBy) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy = "id";

        Page<UserResponse> users = userService.getUsersPage(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                   @RequestBody UserCreationUpdateRequest userCreationUpdateRequest) {
        UserResponse updatedUser = userService.update(userId, userCreationUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable Long userId, @RequestBody Long roleId){
        UserResponse updatedUser = userService.updateRole(userId, roleId);
        return ResponseEntity.ok(updatedUser);
    }

}
