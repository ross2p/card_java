package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.service.CardService;
import ua.edu.lnu.card.service.DeckService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<DeckResponse> create(@RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest,
                                               @AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        DeckResponse newDeck = deckService.createDeck(userId, deckCreationUpdateRequest);
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

    @GetMapping
    public ResponseEntity<Page<DeckResponse>> getAllPublic(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                          @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {

        Page<DeckResponse> comments = deckService.getAllPublicDeck(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{deckId}")
    public ResponseEntity<DeckResponse> update(@PathVariable UUID deckId,
                                               @RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest) {
        DeckResponse updatedDeck = deckService.updateDeck(deckId, deckCreationUpdateRequest);
        return ResponseEntity.ok(updatedDeck);
    }
}
