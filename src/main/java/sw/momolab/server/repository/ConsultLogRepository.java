package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.momolab.server.domain.ConsultLog;

import java.time.LocalDateTime;

public interface ConsultLogRepository extends JpaRepository<ConsultLog, Long> {

    // createdAt이 기준 날짜 이전인 데이터 전부 삭제
    @Modifying
    @Query("DELETE FROM ConsultLog c WHERE c.createdAt < :threshold")
    int deleteOldLogs(@Param("threshold") LocalDateTime threshold);
}
