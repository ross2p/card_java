package ua.edu.lnu.card.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.dto.user.UserResponse;
import ua.edu.lnu.card.entity.Deck;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.utils.enums.AccessLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class}
)
public abstract class DeckMapper {

    @Autowired
    protected UserMapper userMapper;

    @Mapping(source = "collaborator", target = "userAccessLevel", qualifiedByName = "mapCollaboratorsToDto")
    public abstract DeckResponse toDto(Deck deck);

    @Named("mapCollaboratorsToDto")
    protected Map<UserResponse, AccessLevel> mapCollaboratorsToDto(Map<User, AccessLevel> collaborator) {
        System.out.println("userMapper"+userMapper);
        if (collaborator == null) {
            return null;
        }

        Map<UserResponse, AccessLevel> mappedCollaborators = new HashMap<>();
        for (Map.Entry<User, AccessLevel> entry : collaborator.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                mappedCollaborators.put(userMapper.toDto(entry.getKey()), entry.getValue());
            }
        }
        return mappedCollaborators;
    }

    @Mapping(source = "userId", target = "owner.id")
    @Mapping(target = "isPrivate", defaultValue = "true")
    @Mapping(source = "deckCreationUpdateRequest.userAccessLevel", target = "collaborator", qualifiedByName = "mapCollaboratorsToEntity")
    public abstract Deck toEntity(DeckCreationUpdateRequest deckCreationUpdateRequest, UUID userId);

    @Named("mapCollaboratorsToEntity")
    protected Map<User, AccessLevel> mapCollaboratorsToEntity(Map<UUID, AccessLevel> userAccessLevel) {
        if (userAccessLevel == null) {
            return null;
        }

        Map<User, AccessLevel> mappedCollaborators = new HashMap<>();
        for (Map.Entry<UUID, AccessLevel> entry : userAccessLevel.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                User user = new User();
                user.setId(entry.getKey());
                mappedCollaborators.put(user, entry.getValue());
            }
        }
        return mappedCollaborators;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userAccessLevel", target = "collaborator", qualifiedByName = "mapCollaboratorsToEntity")
    public abstract Deck partialUpdate(DeckCreationUpdateRequest deckCreationUpdateRequest, @MappingTarget Deck deck);
}