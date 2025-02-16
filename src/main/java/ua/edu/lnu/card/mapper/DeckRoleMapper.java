package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entity.DeckRole;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeckRoleMapper {
    @Mapping(source = "deckId", target = "deck.id")
    DeckRole toEntity(DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "deckId", target = "deck.id")
    DeckRole partialUpdate(DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest, @MappingTarget DeckRole deckRole);
}