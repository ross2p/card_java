package ua.edu.lnu.card.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.repository.DeckRepository;

import java.util.Objects;

@Component("auth")
@RequiredArgsConstructor
public class AuthComponent {

    private final DeckRepository deckRepository;

    public boolean isMe(Long userId) {
        DefaultUserDetails userDetails = getUserDetails();
        return Objects.equals(userId, userDetails.getId());
    }
    private DefaultUserDetails getUserDetails() {
        return (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public String getUserName() {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }
    public boolean isRole(String role){
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_"+ role));
    }

    public boolean isCollaborator(Long deck){
        return true;        //todo
    }
}
