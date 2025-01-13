package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dto.role.RoleResponse;
import ua.edu.lnu.card.entity.Role;

import java.util.Optional;

public interface RoleService {
    RoleResponse getRoleById(Long id);
}
