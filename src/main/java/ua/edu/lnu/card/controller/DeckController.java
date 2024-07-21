package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.config.AuthComponent;
import ua.edu.lnu.card.dto.collaborator.CollaboratorCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.service.DeckService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeckController {
    private final DeckService deckService;
    private final AuthComponent authComponent;

    @GetMapping("/decks")
    public ResponseEntity<Page<DeckResponse>> getPublicDecks(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {

        Page<DeckResponse> decks = deckService.getPublicDecks(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(decks);
    }
    @GetMapping("/user/{userId}/decks")
    public ResponseEntity<Page<DeckResponse>> getDecksByUserId(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                               @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                               @PathVariable(value = "userId") Long userId) {

        boolean isAdmin = authComponent.isRole("ADMIN");
        boolean isMe = authComponent.isMe(userId);
        boolean isCollaborator = authComponent.isCollaborator(userId);
        Long collaboratorId = authComponent.getUserId();
        PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by(sortBy));
        Page<DeckResponse> decks;

        if (isAdmin || isMe){
            decks = deckService.getDecksByUserId(userId, pageRequest);
        }
        else if (isCollaborator){
            decks = deckService.getDecksByCollaborator(userId, collaboratorId, pageRequest);
        }
        else{
            decks = deckService.getPublicDecksByUserId(userId, pageRequest);
        }
        return ResponseEntity.ok(decks);
    }

    @PostMapping("/user/{userId}/deck")
    @PreAuthorize("@auth.isMe(#userId)")
    public ResponseEntity<DeckResponse> createDeck(@PathVariable Long userId,
                                                   @RequestBody DeckCreationUpdateRequest deckResponse) {
        DeckResponse createdDeck = deckService.create(userId, deckResponse);
        return ResponseEntity.ok(createdDeck);
    }
    @PostMapping("/user/{userId}/deck/{deckId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<DeckResponse> addCollaborator(        @PathVariable Long userId,
                                                                @PathVariable Long deckId,
                                                                @RequestBody CollaboratorCreationUpdateRequest collaboratorCreationUpdateRequest) {
        DeckResponse deckResponse = deckService.addCollaborator(deckId, collaboratorCreationUpdateRequest);

        return ResponseEntity.ok(deckResponse);
    }
    @PutMapping("/user/{userId}/deck/{deckId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<DeckResponse> updateDeck(@PathVariable Long userId,
                                                   @PathVariable Long deckId,
                                                   @RequestBody DeckCreationUpdateRequest deckResponse) {
        DeckResponse updatedDeck = deckService.update(deckId, deckResponse);
        return ResponseEntity.ok(updatedDeck);
    }
    @DeleteMapping("/user/{userId}/deck/{deckId}")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        deckService.delete(deckId);
        return ResponseEntity.noContent().build();
    }
}
