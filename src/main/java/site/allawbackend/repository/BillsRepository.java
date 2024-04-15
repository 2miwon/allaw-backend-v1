package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.allawbackend.entity.Bills;

import java.util.Optional;

public interface BillsRepository extends JpaRepository<Bills, Integer> {

    Optional<Bills> findByBillNo(Integer billNo);

    @Query("SELECT COUNT(b) FROM Bills b")
    long countAll();
}
