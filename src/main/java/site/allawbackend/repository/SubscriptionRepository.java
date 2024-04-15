package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.allawbackend.entity.Keyword;
import site.allawbackend.entity.Subscription;
import site.allawbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserAndKeyword(User user, Keyword keyword);

    @Query("SELECT s.user FROM Subscription s WHERE s.keyword.id = :keywordId")
    Optional<List<User>> findAllUsersByKeyword(Long keywordId);

    @Query("SELECT s.keyword FROM Subscription s WHERE s.user.id = :userId")
    Optional<List<Keyword>> findAllKeywordsByUserId(@Param("userId") Long userId);

}
