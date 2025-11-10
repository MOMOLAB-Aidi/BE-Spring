package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.momolab.server.domain.PatientActivation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PatientActivationRepository extends JpaRepository<PatientActivation, Long> {

    // tokenHash로 유효한 토큰 찾기
    @Query("SELECT pa FROM PatientActivation pa " +
            "WHERE pa.tokenHash = :tokenHash " +
            "AND pa.usedAt IS NULL " +
            "AND pa.expiresAt > :now ")
    Optional<PatientActivation> findValidByHash(@Param("tokenHash") String tokenHash,
                                                @Param("now") LocalDateTime now);

    // 환자당 아직 유효한 토큰이 있는지
    @Query("SELECT COUNT(pa) FROM PatientActivation pa " +
            "WHERE pa.patient.id = :patientId " +
            "AND pa.usedAt is NULL " +
            "AND pa.expiresAt > :now")
    long countActiveByPatient(@Param("patientId") Long patientId,
                              @Param("now") LocalDateTime now);
}
