package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.entity.User;
import site.allawbackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByName(final String username) {
        return repository.findByName(username)
                .orElseThrow(
                        () -> new IllegalArgumentException("no result")
                );
    }
}