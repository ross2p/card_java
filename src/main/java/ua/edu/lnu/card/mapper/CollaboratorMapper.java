package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.CollaboratorResponse;
import ua.edu.lnu.card.entity.AccessLevel;
import ua.edu.lnu.card.entity.Collaborator;
import ua.edu.lnu.card.service.UserService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserService.class})
public interface CollaboratorMapper {

    CollaboratorResponse toDto(Collaborator collaborator);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Collaborator partialUpdate(CollaboratorResponse collaboratorResponse, @MappingTarget Collaborator collaborator);

    @Mapping(target = "user", source = "collaboratorResponse.userId", qualifiedByName = "getUserById")
//    @Mapping(target = "deck.id", source = "deckId")
    Collaborator toEntity(CollaboratorCreationUpdateRequest collaboratorResponse);

    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserById")
//    @Mapping(target = "deck.id", source = "deckId")
    @Mapping(target = "accessLevel", source = "accessLevel")
    Collaborator toEntity(Long userId, AccessLevel accessLevel);

}