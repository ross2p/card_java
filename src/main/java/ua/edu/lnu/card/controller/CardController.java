package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.service.CardService;

import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<CardData> getAllCardByDeckId(@PathVariable UUID cardId) {
        CardData card = cardService.getCardDataById(cardId);
        return ResponseEntity.ok(card);
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody CardCreationUpdateRequest cardCreationUpdateRequest) {
        Card newDeck = cardService.create(cardCreationUpdateRequest);
        return ResponseEntity.ok(newDeck);
    }

}
