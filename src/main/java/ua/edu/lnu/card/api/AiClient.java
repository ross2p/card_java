package ua.edu.lnu.card.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.utils.AIConstants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
public class AiClient {

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.modal}")
    private String modal;

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final List<Message> messages = new ArrayList<>();

    public enum Role {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return value;
        }
    }

    public static class Message {
        private final String role;
        private final String content;

        public Message(Role role, String content) {
            this.role = role.toString();
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }

    public boolean addMessage(Role role, String content) throws JsonProcessingException {
        return messages.add(new Message(role, MAPPER.writeValueAsString(content)));
    }

    public Map<String, Object> sendMessage() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "model", modal,
                "stream", false,
                "messages", messages);

        System.out.println("requestBody: " + MAPPER.writeValueAsString(requestBody));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(MAPPER.writeValueAsString(requestBody)))
                .build();

        String responseBody = CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println("responseBody111: " + responseBody);
        return MAPPER.readValue(responseBody, new TypeReference<Map<String, Object>>() {
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T sendMessage(Class<T> responseType) throws Exception {
        messages.add(0, new Message(Role.SYSTEM, AIConstants.getAiResponseExample(responseType)));

        Map<String, Object> response = this.sendMessage();
        Map<String, Object> firstChoice;
        if (response.get("choices") != null) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            firstChoice = choices.get(0);

        } else {
            firstChoice = response;
        }

        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = (String) message.get("content");
        jsonContent = jsonContent.substring(jsonContent.indexOf("{"), jsonContent.lastIndexOf("}") + 1);
        return objectMapper.readValue(jsonContent, responseType);
    }
}