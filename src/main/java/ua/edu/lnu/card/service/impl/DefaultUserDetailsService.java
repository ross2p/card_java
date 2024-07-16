package ua.edu.lnu.card.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dto.auth.DefaultUserDetails;
import ua.edu.lnu.card.entity.DefaultAuthority;
import ua.edu.lnu.card.entity.User;
import ua.edu.lnu.card.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email address '%s' not found".formatted(email)));
        return DefaultUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(Set.of(DefaultAuthority.builder()
                        .authority(user.getRole().getName())
                        .build()))
                .build();
    }
}
