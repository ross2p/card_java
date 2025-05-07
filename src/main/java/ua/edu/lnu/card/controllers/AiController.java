package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.deck.DescriptionDeck;
import ua.edu.lnu.card.service.AIService;


@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    private final AIService aiService;

    @PostMapping("/generate-card")
    public ResponseEntity<CardCreationUpdateRequest> generateOneCard(@RequestBody CardCreationUpdateRequest cardCreationUpdateRequest) throws Exception {
        return ResponseEntity.ok(aiService.generateOneCard(cardCreationUpdateRequest));
    }

    @PostMapping("/generate-description-deck")
    public ResponseEntity<DescriptionDeck> generateDescriptionDeck(@RequestBody DescriptionDeck deck) throws Exception {
        return ResponseEntity.ok(aiService.generateDescriptionDeck(deck));
    }

}
