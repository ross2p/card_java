package ua.edu.lnu.card.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.dto.deck.DeckRatingCreationRequest;
import ua.edu.lnu.card.dto.deck.DeckRatingResponse;
import ua.edu.lnu.card.service.DeckRatingService;

import java.util.UUID;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class DeckRatingController {
    private final DeckRatingService deckRatingService;

    @PostMapping
    public ResponseEntity<DeckRatingResponse> create(@RequestBody DeckRatingCreationRequest deckRatingCreationRequest,
                                                     @AuthenticationPrincipal DefaultUserDetails userDetails) {
        UUID userId = userDetails.getId();
        DeckRatingResponse newDeckRating = deckRatingService.save(deckRatingCreationRequest, userId);
        return ResponseEntity.ok(newDeckRating);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckRatingResponse> getByDeckId(@PathVariable UUID id) {
        DeckRatingResponse deckRating = deckRatingService.getDeckRatingById(id);
        return ResponseEntity.ok(deckRating);
    }
}
