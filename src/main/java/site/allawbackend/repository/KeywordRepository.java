package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.Keyword;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByContent(String content);
}
