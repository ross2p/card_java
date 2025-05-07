package ua.edu.lnu.card.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dtos.deckRole.DeckRoleCreationUpdateRequest;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.service.DeckRoleService;

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
