package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.momolab.server.domain.Record;

import java.time.LocalDate;
import java.util.Set;

public interface RecordRepository extends JpaRepository<Record, Long> {

    // 특정 기간동안 기록 일정이 있는 모든 날짜 조회
    @Query("SELECT DISTINCT r.recordDate FROM Record r WHERE r.user.id = :userId AND r.recordDate BETWEEN :startDate AND :endDate")
    Set<LocalDate> findDistinctRecordDatesByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
