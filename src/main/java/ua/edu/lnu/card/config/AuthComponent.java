package ua.edu.lnu.card.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.entity.DefaultUserDetails;
import ua.edu.lnu.card.service.DeckService;

import java.util.Objects;

@Component("auth")
@RequiredArgsConstructor
public class AuthComponent {
    private final DeckService deckService;

    public boolean isMe(Long userId) {
        DefaultUserDetails userDetails = getUserDetails();
        return Objects.equals(userId, userDetails.getId());
    }

    public boolean isTodoOwner(Long todoId) {
        DefaultUserDetails userDetails = getUserDetails();
        return deckService.isOwner(todoId, userDetails.getId());
    }

    public boolean isTodoCollaborator(Long todoId) {
        DefaultUserDetails userDetails = getUserDetails();
        return deckService.isCollaborator(todoId, userDetails.getId());
    }

    private DefaultUserDetails getUserDetails() {
        return (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
