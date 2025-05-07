package ua.edu.lnu.card.service;

import ua.edu.lnu.card.dtos.role.RoleResponse;

public interface RoleService {
    RoleResponse getRoleById(Long id);
}
