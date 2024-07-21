package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.service.DeckService;
import ua.edu.lnu.card.service.UserService;

import java.time.Instant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, CollaboratorMapper.class, DeckService.class, UserService.class},
        imports = {Instant.class, AuthComponent.class})
public abstract class DeckMapper {


    @Autowired
    protected AuthComponent authComponent;


    @Mapping(target = "owner", source = "ownerId", qualifiedByName = "getUserById")
    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    public abstract Deck toEntity(DeckCreationUpdateRequest deckCreationUpdateRequest, Long ownerId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    public abstract Deck partialUpdate(DeckCreationUpdateRequest deckCreationUpdateRequest, @MappingTarget Deck deck);

    @Mapping(target = "updatedOn", expression = "java(Instant.now())")
    @Mapping(target = "updatedBy", expression = "java(authComponent.getUserDetailsName())")
    public abstract Deck toEntity(DeckResponse deckResponse);

    public abstract DeckResponse toDto(Deck deck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Deck partialUpdate(DeckResponse deckResponse, @MappingTarget Deck deck);
}