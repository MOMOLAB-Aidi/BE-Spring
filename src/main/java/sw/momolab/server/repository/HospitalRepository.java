package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.Education;

public interface HospitalRepository extends JpaRepository<Education, Long> {

}
