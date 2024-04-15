package site.allawbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.allawbackend.entity.District;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByRegionId(Long regionId);
    Optional<District> findByName(String name);
}
