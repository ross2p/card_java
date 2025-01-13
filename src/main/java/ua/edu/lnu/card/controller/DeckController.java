package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.service.DeckService;

import java.util.UUID;

@RestController
@RequestMapping("/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<Page<DeckResponse>> getByUserId(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
//                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
//                                                         @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
//                                                         @PathVariable(value = "userId") UUID userId) {
//
//        Page<DeckResponse> comments = deckService.getDecksByUserId(userId, PageRequest.of(offset, pageSize, Sort.by(sortBy)));
//        return ResponseEntity.ok(comments);
//    }
//    @GetMapping
//    public ResponseEntity<Page<DeckResponse>> getAllPublic(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
//                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
//                                                          @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
//
//        Page<DeckResponse> comments = deckService.getAllPublic(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
//        return ResponseEntity.ok(comments);
//    }

    @PostMapping
    public ResponseEntity<DeckResponse> create(@RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest,
                                               @AuthenticationPrincipal DefaultUserDetails userDetails) {
        System.out.println("create " + deckCreationUpdateRequest.toString());
        UUID userId = userDetails.getId();
        DeckResponse newDeck = deckService.create(userId, deckCreationUpdateRequest);
        return ResponseEntity.ok(newDeck);
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckResponse> getById(@PathVariable UUID deckId) {
        System.out.println("getById " + deckId.toString());
        DeckResponse deck = deckService.getById(deckId);
        return ResponseEntity.ok(deck);
    }

    @PatchMapping("/{deckId}")
    public ResponseEntity<DeckResponse> update(@PathVariable UUID deckId,
                                               @RequestBody DeckCreationUpdateRequest deckCreationUpdateRequest) {
        System.out.println("update " + deckCreationUpdateRequest.toString());
        DeckResponse updatedDeck = deckService.update(deckId, deckCreationUpdateRequest);
        return ResponseEntity.ok(updatedDeck);
    }
}
