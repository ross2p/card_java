package ua.edu.lnu.card.mappers;

import org.mapstruct.*;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.entities.Card;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    Card toEntity(CardData cardData);

    CardData toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardData cardData, @MappingTarget Card card);

    @Mapping(source = "deckId", target = "deck.id")
    Card toEntity(CardCreationUpdateRequest cardCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "deckId", target = "deck.id")
    Card partialUpdate(CardCreationUpdateRequest cardCreationUpdateRequest, @MappingTarget Card card);
}