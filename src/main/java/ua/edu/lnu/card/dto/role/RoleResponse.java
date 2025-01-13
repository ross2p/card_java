package ua.edu.lnu.card.dto.role;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entity.Role}
 */
public record RoleResponse(UUID id, String name) implements Serializable {
}