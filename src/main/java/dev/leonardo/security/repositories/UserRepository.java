package dev.leonardo.security.repositories;

import dev.leonardo.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
