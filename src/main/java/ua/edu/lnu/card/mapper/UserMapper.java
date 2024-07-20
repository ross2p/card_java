package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.user.AdminCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.repository.RoleRepository;

import java.time.Instant;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING
        , imports = {Instant.class}
        , uses = {AuthComponent.class})
public interface UserMapper {


    UserResponse toDto(User user);

    AdminCreationUpdateRequest toAdminDto(UserCreationUpdateRequest userCreationUpdateRequest, Long roleId);


    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "role.id",  source = "roleId")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    User toEntity(AdminCreationUpdateRequest adminCreationUpdateRequest);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    User partialUpdate(UserCreationUpdateRequest userCreationUpdateRequest, @MappingTarget User user);



    User toEntity(UserResponse userResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserResponse userResponse, @MappingTarget User user);
}