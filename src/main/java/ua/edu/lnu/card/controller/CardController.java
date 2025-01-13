package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.entity.Card;
import ua.edu.lnu.card.service.CardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/{deckId}")
    public ResponseEntity<List<CardData>> getAllCardByDeckId(@PathVariable UUID deckId){
        List<CardData> list =  cardService.getAllByDeckId(deckId);
        return ResponseEntity.ok(list);
    }
    @PostMapping
    public ResponseEntity<Card> create(@RequestBody CardCreationUpdateRequest cardCreationUpdateRequest) {
        Card newDeck = cardService.create(cardCreationUpdateRequest);
        return ResponseEntity.ok(newDeck);
    }


}
