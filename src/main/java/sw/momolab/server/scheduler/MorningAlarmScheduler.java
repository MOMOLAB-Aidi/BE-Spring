package sw.momolab.server.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 매일 아침 9시 동작
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    @Transactional(readOnly = true)
    public void sendMorningAlarm() {
        log.info("[FCM] 매일 아침 9시 알림 스케줄러 실행 시작");

        List<FcmToken> activeTokens = fcmTokenRepository.findAllByIsActiveTrue();

        log.info("[FCM] 활성 토큰 개수: {}", activeTokens.size());

        String title = "오늘의 투석 계획을 확인해 볼까요?";
        String body = "하루 최대 5회까지 투석 가능! 투석이 끝난 뒤에는 에이디에서 바로 기록해 주세요.";

        for (FcmToken tokenEntity : activeTokens) {
            String token = tokenEntity.getToken();
            fcmNotificationService.sendToToken(token, title, body);
        }

        log.info("[FCM] 매일 아침 9시 알림 스케줄러 실행 종료");
    }
}
