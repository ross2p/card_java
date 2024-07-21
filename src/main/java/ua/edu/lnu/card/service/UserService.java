package ua.edu.lnu.card.service;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;

public interface UserService {
    @Named("getUserById")
    User getUserById(Long id);
    UserResponse getById(Long userId);

    UserResponse create(UserCreationUpdateRequest userCreationRequest);

    UserResponse update(Long id, UserCreationUpdateRequest userCreationUpdateRequest);

    void delete(Long id);

    Page<UserResponse> getUsersPage(PageRequest of);

    UserResponse updateRole(Long userId, Long roleId);
}
