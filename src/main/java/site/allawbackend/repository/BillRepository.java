package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.Bill;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNo(Integer billNo);
    long count();
}
