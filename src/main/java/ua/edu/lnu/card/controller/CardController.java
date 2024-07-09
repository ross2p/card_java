package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lnu.card.service.CardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/decks/{deckId}/cards")
public class CardController {
    private final CardService cardService;


}
