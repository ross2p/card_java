package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.api.AiClient;
import ua.edu.lnu.card.dto.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dto.card.CardData;
import ua.edu.lnu.card.dto.deck.DeckResponse;
import ua.edu.lnu.card.dto.deck.DescriptionDeck;
import ua.edu.lnu.card.service.AIService;
import ua.edu.lnu.card.service.CardService;
import ua.edu.lnu.card.service.DeckService;
import ua.edu.lnu.card.utils.AIConstants;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
    private final DeckService deckService;
    private final CardService cardService;
    private final AiClient aiClient;
    public CardCreationUpdateRequest generateOneCard(CardCreationUpdateRequest cardCreationUpdateRequest) throws Exception {
        DeckResponse deckResponse = deckService.getDeckDtoById(cardCreationUpdateRequest.getDeckId());
        List<CardData> cardDeckList = cardService.getAllByDeckId(cardCreationUpdateRequest.getDeckId());

        aiClient.addMessage(AiClient.Role.SYSTEM, AIConstants.generateOneCard(cardCreationUpdateRequest));
        aiClient.addMessage(AiClient.Role.USER, "Deck: "+ deckResponse.toString() + " Cards: " + cardDeckList.toString());

        return aiClient.sendMessage(CardCreationUpdateRequest.class);
    }

    @Override
    public DescriptionDeck generateDescriptionDeck(DescriptionDeck descriptionDeck) throws Exception {
        DeckResponse deckResponse = deckService.getDeckDtoById(descriptionDeck.getId());
        List<CardData> cardDeckList = cardService.getAllByDeckId(descriptionDeck.getId());

        aiClient.addMessage(AiClient.Role.SYSTEM, AIConstants.generateDescriptionDeck(descriptionDeck));
        aiClient.addMessage(AiClient.Role.USER, "Deck: "+ deckResponse.toString() + " Cards: " + cardDeckList.toString());

        return aiClient.sendMessage(DescriptionDeck.class);
    }
}
