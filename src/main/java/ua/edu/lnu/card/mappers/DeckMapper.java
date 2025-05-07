package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.dto.deck.DescriptionDeck;
import ua.edu.lnu.card.entity.Deck;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeckMapper {
    @Mapping(target = "owner.id", source = "userId")
    Deck toEntity(DeckCreationUpdateRequest deckCreationUpdateRequest, UUID userId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckCreationUpdateRequest deckCreationUpdateRequest, @MappingTarget Deck deck);

    DeckResponse toDto(Deck deck);

    Deck toEntity(DescriptionDeck descriptionDeck);

    DescriptionDeck toDto1(Deck deck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DescriptionDeck descriptionDeck, @MappingTarget Deck deck);
}