package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface DeckMapper {
    DeckResponse toDto(Deck deck);

    Deck toEntity(DeckCreationUpdateRequest deckCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckCreationUpdateRequest deckCreationUpdateRequest, @MappingTarget Deck deck);
}