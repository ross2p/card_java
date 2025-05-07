package ua.edu.lnu.card.mappers;

import org.mapstruct.*;
import ua.edu.lnu.card.dtos.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dtos.deck.DeckRatingResponse;
import ua.edu.lnu.card.entities.DeckRating;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeckRatingMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deckId", target = "deck.id")
    @Mapping(source = "rating", target = "rating")
    DeckRating toEntity(Double rating, UUID deckId, UUID userId);


    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeckRating partialUpdate(DeckRatingCreationRequest deckRatingCreationRequest, @MappingTarget DeckRating deckRating);

    @Mapping(source = "userRating", target = "rating")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "deckId", target = "deck.id")
    DeckRating toEntity(DeckRatingResponse deckRatingResponse);

    @InheritInverseConfiguration(name = "toEntity")
    DeckRatingResponse toDto(DeckRating deckRating);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeckRating partialUpdate(DeckRatingResponse deckRatingResponse, @MappingTarget DeckRating deckRating);
}