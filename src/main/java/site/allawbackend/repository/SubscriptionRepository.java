package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.Keyword;
import site.allawbackend.entity.Subscription;
import site.allawbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserAndKeyword(User user, Keyword keyword);

    Optional<List<Keyword>> findAllKeywordsByUser_Id(Long userId);
}
