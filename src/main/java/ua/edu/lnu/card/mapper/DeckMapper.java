package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.entity.Deck;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, CollaboratorMapper.class})
public interface DeckMapper {

    Deck toEntity(DeckCreationUpdateRequest deckCreationUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckCreationUpdateRequest deckCreationUpdateRequest, @MappingTarget Deck deck);


    Deck toEntity(DeckResponse deckResponse);

    @AfterMapping
    default void linkCollaborators(@MappingTarget Deck deck) {
        deck.getCollaborators().forEach(collaborator -> collaborator.setDeck(deck));
    }

    DeckResponse toDto(Deck deck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckResponse deckResponse, @MappingTarget Deck deck);
}