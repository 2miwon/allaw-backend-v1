package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String username);
}
