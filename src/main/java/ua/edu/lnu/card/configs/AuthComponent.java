package ua.edu.lnu.card.configs;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;

import java.util.Objects;
import java.util.UUID;

@Component("auth")
@RequiredArgsConstructor
public class AuthComponent {

    private DefaultUserDetails getUserDetails() {
        return (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public boolean isMe(UUID userId) {
        DefaultUserDetails userDetails = getUserDetails();
        return Objects.equals(userId, userDetails.getId());
    }
    public boolean isRegistered(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }
    public String getUserDetailsName() {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getUsername();
    }

    private final PasswordEncoder passwordEncoder;

    @Named("encodedPassword")
    public String encodedPassword(String password){
        return  passwordEncoder.encode(password);
    }
    public UUID getUserId() {
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getId();
    }
    public boolean isRole(String role){
        DefaultUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_"+ role));
    }
}
