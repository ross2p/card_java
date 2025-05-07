package ua.edu.lnu.card.dtos.user;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ua.edu.lnu.card.entities.User}
 */
@Value
public class UserResponseWithAccessLevel extends UserResponse implements Serializable {

}