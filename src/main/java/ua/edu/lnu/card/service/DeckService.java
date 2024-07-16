package ua.edu.lnu.card.service;

public interface DeckService {
    boolean isOwner(Long todoId, Long id);

    boolean isCollaborator(Long todoId, Long id);
}
