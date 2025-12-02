package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sw.momolab.server.domain.Education;

import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query(value = "SELECT * FROM education WHERE is_active = true ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Education> findRandomActiveTip();
}