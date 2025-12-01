package sw.momolab.server.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.domain.FcmToken;
import sw.momolab.server.repository.FcmTokenRepository;
import sw.momolab.server.service.fcmService.FcmNotificationService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MorningAlarmScheduler {

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmNotificationService fcmNotificationService;

    @Value("${fcm.morning-alarm.title}")
    private String morningAlarmTitle;

    @Value("${fcm.morning-alarm.body}")
    private String morningAlarmBody;

    // 매일 아침 9시 동작
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void sendMorningAlarm() {
        log.info("[FCM] 매일 아침 9시 알림 스케줄러 실행 시작");

        List<FcmToken> activeTokens = fetchActiveTokens();

        log.info("[FCM] 활성 토큰 개수: {}", activeTokens.size());

        int successCount = 0;
        int failCount = 0;

        for (FcmToken tokenEntity : activeTokens) {
            String token = tokenEntity.getToken();
            try {
                fcmNotificationService.sendToToken(token, morningAlarmTitle, morningAlarmBody);
                successCount++;
            } catch (Exception e) {
                failCount++;
                log.error("[FCM] 알림 전송 실패 - 토큰 ID: {}, 오류: {}", tokenEntity.getId(), e.getMessage(), e);
            }
        }

        log.info("[FCM] 매일 아침 9시 알림 스케줄러 실행 종료 - 성공: {}, 실패: {}", successCount, failCount);
    }

    @Transactional(readOnly = true)
    protected List<FcmToken> fetchActiveTokens() {
        return fcmTokenRepository.findAllByIsActiveTrue();
    }
}