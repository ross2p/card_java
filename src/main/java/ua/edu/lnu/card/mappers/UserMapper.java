package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class, AuthComponent.class})
public interface UserMapper {
    UserResponse toDto(User user);

    @Mapping(target = "password", source = "password", qualifiedByName = "encodedPassword")
    User toEntity(UserCreationUpdateRequest userCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserCreationUpdateRequest userCreationUpdateRequest, @MappingTarget User user);

    DefaultUserDetails toPayload(User user);

    User toEntity(UserResponse userResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserResponse userResponse, @MappingTarget User user);
}