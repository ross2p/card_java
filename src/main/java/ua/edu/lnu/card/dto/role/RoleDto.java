package ua.edu.lnu.card.dto.role;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Role}
 */
public record RoleDto(Integer id, String name) implements Serializable {
}