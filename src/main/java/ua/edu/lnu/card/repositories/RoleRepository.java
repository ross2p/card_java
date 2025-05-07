package ua.edu.lnu.card.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entities.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String roleName);
}