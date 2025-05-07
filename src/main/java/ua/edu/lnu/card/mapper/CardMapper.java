package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;

import java.util.UUID;

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