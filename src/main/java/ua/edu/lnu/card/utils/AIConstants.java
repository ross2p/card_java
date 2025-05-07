package ua.edu.lnu.card.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.edu.lnu.card.dtos.card.CardCreationUpdateRequest;
import ua.edu.lnu.card.dtos.deck.DescriptionDeck;


public class AIConstants {
    private static final String AI_RESPONSE_FORMAT = "Reply only in JSON format, without any extra text. Don't explain anything or add anything extra";

    private static String toJsonExample(Class<?> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object exampleInstance = clazz.getDeclaredConstructor().newInstance();
            return objectMapper.writeValueAsString(exampleInstance);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static String getAiResponseExample(Class<?> clazz) {
        return AI_RESPONSE_FORMAT + " Example Response: " + toJsonExample(clazz);
    }

    public static String generateOneCard(CardCreationUpdateRequest cardCreationUpdateRequest) {
        return "You help you create or complete the flash cards for tests. Based on the test name, description and existing questions, fill in this object here: " +
                cardCreationUpdateRequest.toString() +
                "The key is the question, Value is the answer, description is an explanation why such an answer is correct.";
    }

    public static String generateDescriptionDeck(DescriptionDeck descriptionDeck) {
        return "You are helping to create or complete a description for tests. Based on the test name and existing questions and answers, fill in this object here: " +
                descriptionDeck.toString();
    }
}
