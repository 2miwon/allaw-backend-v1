package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.allawbackend.entity.Opinion;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
//    Optional<Subscription> findByUserAndKeyword(User user, Keyword keyword);
//
//    @Query("SELECT s.user FROM Subscription s WHERE s.keyword.id = :keywordId")
//    Optional<List<User>> findAllUsersByKeyword(Long keywordId);
//
    @Query("SELECT o FROM Opinion o WHERE o.bills.billNo = :billsNo ORDER BY o.id DESC")
    Optional<List<Opinion>> findAllByBillsId(@Param("billsNo") int billsNo);


    @Query("SELECT o FROM Opinion o WHERE o.bills.billNo = :billsNo ORDER BY o.id DESC Limit 8")
    Optional<List<Opinion>> findByBillsId(@Param("billsNo") int billsNo);
}
