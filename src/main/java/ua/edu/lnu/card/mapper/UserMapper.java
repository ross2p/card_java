package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.entity.User;

import java.time.Instant;


@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING
        , imports = {Instant.class,AuthComponent.class, DefaultUserDetails.class, SecurityContextHolder.class}
        , uses = { AuthComponent.class})
public abstract class   UserMapper {
    @Autowired
    protected AuthComponent authComponent;

    public abstract UserResponse toDto(User user);

    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodedPassword")
    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    public abstract User toEntity(UserCreationUpdateRequest adminCreationUpdateRequest);
    @AfterMapping
    void getUpdateBy(@MappingTarget User user){
        if (authComponent.isRegistered()) {
            user.setUpdatedBy(authComponent.getUserDetailsName());
        }else{
            user.setUpdatedBy(user.getEmail());
        }
        user.setRole(new Role(1L, "USER"));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodedPassword")
    public abstract User partialUpdate(UserCreationUpdateRequest userCreationUpdateRequest, @MappingTarget User user);
}