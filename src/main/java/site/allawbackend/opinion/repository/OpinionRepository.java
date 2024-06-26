package site.allawbackend.opinion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.Bill;
import site.allawbackend.opinion.entity.Opinion;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    Optional<List<Opinion>> findByBill(Bill bill);
}
