package ua.edu.lnu.card.dtos.role;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ua.edu.lnu.card.entities.Role}
 */
public record RoleResponse(UUID id, String name) implements Serializable {
}