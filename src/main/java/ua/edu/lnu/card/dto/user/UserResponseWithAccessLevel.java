package ua.edu.lnu.card.dto.user;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entity.User}
 */
@Value
public class UserResponseWithAccessLevel extends UserResponse  implements Serializable{

}