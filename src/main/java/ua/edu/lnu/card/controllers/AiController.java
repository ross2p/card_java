package ua.edu.lnu.card.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DescriptionDeck;
import ua.edu.lnu.card.services.AIService;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    private final AIService aiService;

    @PostMapping("/generate-card")
    public ResponseEntity<CardCreationUpdateRequest> generateOneCard(
            @RequestBody CardCreationUpdateRequest cardCreationUpdateRequest) throws Exception {
        return ResponseEntity.ok(aiService.generateOneCard(cardCreationUpdateRequest));
    }

    @PostMapping("/generate-description-deck")
    public ResponseEntity<DescriptionDeck> generateDescriptionDeck(@RequestBody DescriptionDeck deck) throws Exception {
        return ResponseEntity.ok(aiService.generateDescriptionDeck(deck));
    }

}
