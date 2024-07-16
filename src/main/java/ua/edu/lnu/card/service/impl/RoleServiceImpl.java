package ua.edu.lnu.card.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.lnu.card.entity.Role;
import ua.edu.lnu.card.repository.RoleRepository;
import ua.edu.lnu.card.service.RoleService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public String getRoleNameById(Long id) {
        return getRoleById(id).get().getName();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return Optional.ofNullable(roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id '%d' not found".formatted(id))));
    }
}
