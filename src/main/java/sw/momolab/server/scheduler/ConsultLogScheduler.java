package sw.momolab.server.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.repository.ConsultLogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final ConsultLogRepository consultLogRepository;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteOldConsultLogs() {
        try {
            LocalDateTime threshold = LocalDateTime.now().minusDays(30);
            int deletedCount = consultLogRepository.deleteOldLogs(threshold);
            log.info("[ConsultLogCleanup] 30일 지난 로그 " + deletedCount + "건 삭제");
        } catch (Exception e) {
            log.error("[ConsultLogCleanup] 상담 기록 삭제 실패", e);
        }
    }
}

