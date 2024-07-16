package ua.edu.lnu.card.service;

import ua.edu.lnu.card.entity.Role;

import java.util.Optional;

public interface RoleService {
    String getRoleNameById(Long id);
    Optional<Role> getRoleById(Long id);
}
