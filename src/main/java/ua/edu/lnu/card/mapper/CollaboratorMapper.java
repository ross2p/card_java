package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.entity.Collaborator;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CollaboratorMapper {
    @Mapping(source = "deckRoleId", target = "deckRole.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deckId", target = "deck.id")
    Collaborator toEntity(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest);

    @InheritInverseConfiguration(name = "toEntity")
    CollaboratorCreationUpdateRequest toDto(Collaborator collaborator);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Collaborator partialUpdate(CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest, @MappingTarget Collaborator collaborator);
}