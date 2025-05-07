package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.mappers.DeckRoleMapper;
import ua.edu.lnu.card.repository.DeckRoleRepository;
import ua.edu.lnu.card.service.DeckRoleService;
import ua.edu.lnu.card.utils.DefaultDeckRoles;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckRoleServiceImpl implements DeckRoleService {
    private final DeckRoleRepository deckRoleRepository;
    private final DeckRoleMapper deckRoleMapper;

    @Override
    public List<DeckRole> createDefaultDeckRole(UUID deckId) {
        List<DeckRoleCreationUpdateRequest> defaultDeckRoleList = DefaultDeckRoles.getDefaultDeckRoleList(deckId);

        return defaultDeckRoleList.stream().map(deckRoleCreationUpdateRequest -> {
            deckRoleCreationUpdateRequest.setDeckId(deckId);
            System.out.println("DeckRoleServiceImpl.createDefaultDeckRole: " + deckRoleCreationUpdateRequest);
            return this.createDeckRole(deckRoleCreationUpdateRequest);
        }).toList();
    }

    @Override
    public DeckRole createDeckRole(DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest) {
        System.out.println("DeckRoleServiceImpl.createDeckRole: " + deckRoleCreationUpdateRequest);
        return deckRoleRepository.saveAndFlush(deckRoleMapper.toEntity(deckRoleCreationUpdateRequest));
    }

    @Override
    public List<DeckRole> getDeckRolesByDeckId(UUID deckId) {
        return deckRoleRepository.findAllByDeckId(deckId);
    }

    @Override
    public DeckRole updateDeckRole(UUID deckRoleId, DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest) {
        DeckRole deckRole = this.getDeckRoleById(deckRoleId);

        return deckRoleMapper.partialUpdate(deckRoleCreationUpdateRequest, deckRole);
    }

    @Override
    public void deleteDeckRole(UUID deckRoleId) {
        throw new InternalServerError("DeckRoleService >> deleteDeckRole >>  Not implemented yet");
    }

    @Override
    public DeckRole getDeckRoleById(UUID deckRoleId) {
        return deckRoleRepository.findById(deckRoleId)
                .orElseThrow(() -> new RuntimeException("Deck role with id %s not found".formatted(deckRoleId)));
    }

    @Override
    public DeckRole getDeckRoleByDeckIdAndName(UUID deckId, String name) {
        return deckRoleRepository.findByDeckIdAndName(deckId, name)
                .orElseThrow(() -> new RuntimeException(
                        "Deck role with deck id %s and name %s not found".formatted(deckId, name)));
    }
}
