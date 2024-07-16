package ua.edu.lnu.card.dto.user;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminCreationUpdateRequest extends UserCreationUpdateRequest implements Serializable {
    private Long roleId;
}