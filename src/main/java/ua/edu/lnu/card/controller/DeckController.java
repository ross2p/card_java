package ua.edu.lnu.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.lnu.card.service.DeckService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/decks")
public class DeckController {
    private final DeckService deckService;
}
