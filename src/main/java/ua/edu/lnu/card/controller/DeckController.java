package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.deck.DeckCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.service.DeckService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeckController {
    private final DeckService deckService;

    @GetMapping("/decks")
    public ResponseEntity<Page<DeckResponse>> getDecks(@RequestParam(value = "offset", required = false) Integer offset,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", required = false) String sortBy) {

        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy ="id";

        Page<DeckResponse> decks = deckService.getPublicDecks(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(decks);
    }
    @GetMapping("/user/{userId}/decks")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<Page<DeckResponse>> getDecksByUserId(@RequestParam(value = "offset", required = false) Integer offset,
                                                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                               @RequestParam(value = "sortBy", required = false) String sortBy,
                                                               @RequestParam(value = "userId") Long userId) {


        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(StringUtils.isEmpty(sortBy)) sortBy = "id";


//        Page<DeckResponse> decks = deckService.getDecksByUserId(userId, PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        Page<DeckResponse> decks = deckService.getPublicDecksByUserId(userId, PageRequest.of(offset, pageSize, Sort.by(sortBy)));
        return ResponseEntity.ok(decks);
    }

    @PostMapping("/user/{userId}/deck")
    @PreAuthorize("hasRole('ADMIN') or @auth.isMe(#userId)")
    public ResponseEntity<DeckResponse> createDeck(@PathVariable Long userId,
                                                   @RequestBody DeckCreationUpdateRequest deckResponse) {
        DeckResponse createdDeck = deckService.create(userId, deckResponse);
        return ResponseEntity.ok(createdDeck);
    }


}
