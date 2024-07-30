package ua.edu.lnu.card.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.service.CollaboratorService;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    public boolean isCollaborator(Long userId, Long deckId) {
        return collaboratorRepository.existsByUser_IdAndDeck_Id(userId, deckId);
    }
}
