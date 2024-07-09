package ua.edu.lnu.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lnu.card.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}