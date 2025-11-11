package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByLoginId(String loginId);
}
