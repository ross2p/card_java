package ua.edu.lnu.card.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.dtos.auth.DefaultAuthority;
import ua.edu.lnu.card.dtos.auth.DefaultUserDetails;
import ua.edu.lnu.card.entities.User;
import ua.edu.lnu.card.mappers.RoleMapper;
import ua.edu.lnu.card.repositories.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
        private final UserRepository userRepository;
        private final RoleMapper roleMapper;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmailIgnoreCase(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User with email address '%s' not found".formatted(email)));
                return DefaultUserDetails.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .password(user.getPassword())
                                .role(roleMapper.toDto(user.getRole()))
                                .authorities(Set.of(DefaultAuthority.builder()
                                                .authority(user.getRole().getName())
                                                .build()))
                                .build();
        }

}
