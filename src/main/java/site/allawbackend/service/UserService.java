package site.allawbackend.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.allawbackend.entity.User;
import site.allawbackend.repository.UserRepository;

@Component
@Transactional
@Primary
public class UserService{

    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByName(final String username) {
        return repository.findByName(username)
                .orElseThrow(
                        () -> new IllegalArgumentException("no result")
                );
    }
}