package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}