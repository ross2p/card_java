package ua.edu.lnu.card.config;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.repository.CollaboratorRepository;
import ua.edu.lnu.card.repository.DeckRepository;
import ua.edu.lnu.card.service.CollaboratorService;

import java.util.Objects;

@Component("auth")
//@Named("auth")
@RequiredArgsConstructor
@Named("AuthComponent")
public class AuthComponent {

    private final CollaboratorService collaboratorService;

    private DefaultUserDetails getUserDetails() {
        return (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public boolean isMe(Long userId) {
        DefaultUserDetails userDetails = getUserDetails();
        return Objects.equals(userId, userDetails.getId());
    }
    @Named("getUsername")
    public String getUserName() {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }
    public String getUserName(String name) {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }
    public Long getUserId() {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getId();
    }
    public boolean isRole(String role){
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_"+ role));
    }
    public boolean isCollaborator(Long deckId){
        DefaultUserDetails userDetails = getUserDetails();
        return collaboratorService.isCollaborator(userDetails.getId(), deckId);
    }
    public boolean isOwner(Long deckId){
        DefaultUserDetails userDetails = getUserDetails();
        return collaboratorService.isOwner(userDetails.getId(), deckId);
    }
}
