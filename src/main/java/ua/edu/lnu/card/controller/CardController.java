package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardResponse;
import ua.edu.lnu.card.service.CardService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{userId}/deck/{deckId}")
public class CardController {
    private final CardService cardService;

    @GetMapping("card/{cardId}")
    @PreAuthorize("@auth.isMe(#userId) or @auth.isCollaborator(#deckId) or hasRole('ADMIN')")
    public @ResponseBody ResponseEntity<CardResponse> getCard(@PathVariable Long userId,
                                                              @PathVariable Long deckId,
                                                              @PathVariable Long cardId) {
        CardResponse card = cardService.readById(cardId);
        return ResponseEntity.ok(card);
    }

    @GetMapping("cards")
    @PreAuthorize("@auth.isMe(#userId) or @auth.isCollaborator(#deckId) or hasRole('ADMIN')")
    public @ResponseBody ResponseEntity<Page<CardResponse>> getAllCards(@PathVariable Long userId,
                                                                        @PathVariable Long deckId,
                                                                        @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                        @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by(sortBy));
        Page<CardResponse> cards = cardService.getAllByDeckId(deckId, pageRequest);
        return ResponseEntity.ok(cards);
    }
    @PostMapping("card")
    @PreAuthorize("@auth.isMe(#userId) or @auth.isCollaborator(#deckId) or hasRole('ADMIN')")
    public @ResponseBody ResponseEntity<CardResponse> createCard(@PathVariable Long userId,
                                                                 @PathVariable Long deckId,
                                                                 @RequestBody CardCreationUpdateRequest cardDto) {
        CardResponse createdCard = cardService.create(deckId, cardDto);
        return ResponseEntity.ok(createdCard);
    }
    @PutMapping("card/{cardId}")
    @PreAuthorize("@auth.isMe(#userId) or @auth.isCollaborator(#deckId) or hasRole('ADMIN')")
    public @ResponseBody ResponseEntity<CardResponse> updateCard(@PathVariable Long userId,
                                                                 @PathVariable Long deckId,
                                                                 @PathVariable Long cardId,
                                                                 @RequestBody CardCreationUpdateRequest cardDto) {
        CardResponse updatedCard = cardService.update(cardId, cardDto);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("card/{cardId}")
    @PreAuthorize("@auth.isMe(#userId) or @auth.isCollaborator(#deckId) or hasRole('ADMIN')")
    public @ResponseBody ResponseEntity<Void> deleteCard(@PathVariable Long userId,
                                                         @PathVariable Long deckId,
                                                         @PathVariable Long cardId) {
        cardService.delete(cardId);
        return ResponseEntity.noContent().build();
    }



}
