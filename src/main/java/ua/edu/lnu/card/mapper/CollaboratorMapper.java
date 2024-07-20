package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.deck.CollaboratorResponse;
import ua.edu.lnu.card.entity.Collaborator;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CollaboratorMapper {
    Collaborator toEntity(CollaboratorResponse collaboratorResponse);

    CollaboratorResponse toDto(Collaborator collaborator);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Collaborator partialUpdate(CollaboratorResponse collaboratorResponse, @MappingTarget Collaborator collaborator);

//    @Mapping(source = "collaboratorResponse.userId", target = "user.id")
//    @Mapping(source = "deskId", target = "deck.id")
//    @Mapping(target = "id", expression = "java(Coll)")
//    Collaborator toEntity(CollaboratorCreationUpdateRequest collaboratorResponse, Long deskId);

}