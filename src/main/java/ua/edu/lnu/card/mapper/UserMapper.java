package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.user.AdminCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.entity.User;

import java.time.Instant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    User toEntity(UserCreationUpdateRequest userCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserCreationUpdateRequest userCreationUpdateRequest, @MappingTarget User user);

    @AfterMapping
    default void setDefaultValues(UserCreationUpdateRequest dto, @MappingTarget User entity) {
        if (entity.getRole() == null) {
            entity.setRole(getDefaultRole());
        }
        defaultValues(dto, entity);
    }

    default void defaultValues(UserCreationUpdateRequest dto, @MappingTarget User entity) {
        if (entity.getUpdatedOn() == null) {
            entity.setUpdatedOn(Instant.now());
        }
        if (entity.getUpdatedBy() == null) {
            entity.setUpdatedBy(dto.getEmail());
        }
    }

    default Role getDefaultRole() {
        return new Role(2, "ADMIN");
    }


    @AfterMapping
    default void setDefaultValues(AdminCreationUpdateRequest dto, @MappingTarget User entity) {
        defaultValues(dto, entity);
    }


    UserResponse toDto(User user);
}
