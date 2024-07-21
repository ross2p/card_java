package ua.edu.lnu.card.service;

public interface CollaboratorService {
    boolean isCollaborator(Long userId, Long deckId);
    boolean isOwner(Long userId, Long deckId);


}
