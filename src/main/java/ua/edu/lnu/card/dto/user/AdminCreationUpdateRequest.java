package ua.edu.lnu.card.dto.user;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
public class AdminCreationUpdateRequest extends UserCreationUpdateRequest implements Serializable {
    Long roleId;
}