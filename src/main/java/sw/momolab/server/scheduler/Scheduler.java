package sw.momolab.server.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.repository.ConsultLogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Scheduler {

    private final ConsultLogRepository consultLogRepository;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteOldConsultLogs() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        int deletedCount = consultLogRepository.deleteOldLogs(threshold);
        System.out.println("[ConsultLogCleanup] 30일 지난 로그 " + deletedCount + "건 삭제");
    }
}

