package ua.edu.lnu.card.services;

import ua.edu.lnu.card.dtos.role.RoleResponse;

public interface RoleService {
    RoleResponse getRoleById(Long id);
}
