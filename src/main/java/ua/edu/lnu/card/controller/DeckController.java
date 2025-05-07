package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.dtos.card.CardData;
import ua.edu.lnu.card.dtos.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DeckResponse;
import ua.edu.lnu.card.entities.Collaborator;
import ua.edu.lnu.card.entities.Deck;
import ua.edu.lnu.card.entities.DeckRole;
import ua.edu.lnu.card.services.CardService;
import ua.edu.lnu.card.services.CollaboratorService;
import ua.edu.lnu.card.services.DeckRoleService;
import ua.edu.lnu.card.services.DeckService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;
    private final DeckRoleService deckRoleService;
    private final CollaboratorService collaboratorService;

    @PostMapping
    public ResponseEntity<Deck> create(@RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest,
            @AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        Deck newDeck = deckService.createDeck(userId, deckCreationUpdateRequest);
        return ResponseEntity.ok(newDeck);
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckResponse> getById(@PathVariable UUID deckId) {
        DeckResponse deck = deckService.getDeckDtoById(deckId);
        return ResponseEntity.ok(deck);
    }

    @GetMapping("/{deckId}/cards")
    public ResponseEntity<List<CardData>> getCardByDeckId(@PathVariable UUID deckId) {
        List<CardData> card = cardService.getAllByDeckId(deckId);
        return ResponseEntity.ok(card);
    }

    @GetMapping("{deckId}/deck-roles")
    public ResponseEntity<List<DeckRole>> getDeckRolesByDeckId(@PathVariable UUID deckId) {
        List<DeckRole> deckRoles = deckRoleService.getDeckRolesByDeckId(deckId);
        return ResponseEntity.ok(deckRoles);
    }

    @GetMapping("/{deckId}/collaborators")
    public ResponseEntity<List<Collaborator>> getCollaboratorsByDeckId(@PathVariable UUID deckId) {
        List<Collaborator> collaborators = collaboratorService.getCollaboratorsByDeckId(deckId);
        return ResponseEntity.ok(collaborators);
    }

    @GetMapping
    public ResponseEntity<Page<DeckResponse>> getAllPublic(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Page<DeckResponse> comments = deckService.getAllPublicDeck(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{deckId}")
    public ResponseEntity<Deck> update(@PathVariable UUID deckId,
                                       @RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest) {
        Deck updatedDeck = deckService.updateDeck(deckId, deckCreationUpdateRequest);
        return ResponseEntity.ok(updatedDeck);
    }
}
