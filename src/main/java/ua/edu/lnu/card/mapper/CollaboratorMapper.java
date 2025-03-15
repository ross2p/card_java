package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.collaborator.CollaboratorResponse;
import ua.edu.lnu.card.entity.Collaborator;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface CollaboratorMapper {
    @Mapping(source = "deckRoleId", target = "deckRole.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deckId", target = "deck.id")
    Collaborator toEntity(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Collaborator partialUpdate(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest, @MappingTarget Collaborator collaborator);


    @Mapping(source = "deck.id", target = "deckId")
    @Mapping(source = "deckRole.id", target = "deckRoleId")
    CollaboratorResponse toDto(Collaborator collaborator);
}