package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardResponse;
import ua.edu.lnu.card.entity.Card;

import java.time.Instant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AuthComponent.class},
        imports = {Instant.class})
public abstract class CardMapper {
    @Autowired
    protected AuthComponent authComponent;
    public abstract CardResponse toDto(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deck.id", source = "deckId")
    @Mapping(target = "updatedOn",expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    public abstract Card toEntity(CardCreationUpdateRequest cardCreationUpdateRequest, Long deckId);

    @Mapping(target = "updatedOn",expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract  Card partialUpdate(CardCreationUpdateRequest cardCreationUpdateRequest, @MappingTarget Card card);
}