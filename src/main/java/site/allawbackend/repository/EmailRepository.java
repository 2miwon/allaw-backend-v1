package site.allawbackend.repository;

import site.allawbackend.entity.Email;
import site.allawbackend.entity.User;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findByReceiver(@NotNull User receiver);
}