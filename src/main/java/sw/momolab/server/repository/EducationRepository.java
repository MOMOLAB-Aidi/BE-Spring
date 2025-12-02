package sw.momolab.server.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sw.momolab.server.domain.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query("SELECT e FROM Education e WHERE e.isActive = true ORDER BY function('RANDOM')")
    List<Education> findRandomActiveTips(Pageable pageable);
}
