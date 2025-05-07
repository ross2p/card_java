package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entity.DeckRole;
import ua.edu.lnu.card.services.DeckRoleService;

@RestController
@RequestMapping("/deck-role")
@RequiredArgsConstructor
public class DeckRoleController {
    private final DeckRoleService deckRoleService;

    @PostMapping
    public ResponseEntity<DeckRole> createDeckRole(@RequestBody DeckRoleCreationUpdateRequest deckRoleCreationUpdateRequest) {
        DeckRole deckRole = deckRoleService.createDeckRole(deckRoleCreationUpdateRequest);
        return ResponseEntity.ok(deckRole);
    }
}
