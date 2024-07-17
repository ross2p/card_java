package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.user.AdminCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserCreationUpdateRequest;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.User;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    UserResponse toDto(User user);


    AdminCreationUpdateRequest toAdminDto(UserCreationUpdateRequest userCreationUpdateRequest, Long roleId);

    User toEntity(AdminCreationUpdateRequest adminCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserCreationUpdateRequest userCreationUpdateRequest, @MappingTarget User user);


}