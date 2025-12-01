package sw.momolab.server.service.fcmService;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmNotificationServiceImpl implements FcmNotificationService {

    private final FcmTokenService fcmTokenService;

    // 단일 토큰에 알림 전송
    @Override
    public void sendToToken(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 메시지 전송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            // 토큰이 유효하지 않거나 등록 해제된 경우
            if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED ||
                    e.getMessagingErrorCode() == MessagingErrorCode.INVALID_ARGUMENT) {
                log.warn("유효하지 않은 FCM 토큰 감지, 비활성화 처리: {}", token);
                fcmTokenService.deactivateByToken(token);
            } else {
                log.error("FCM 메시지 전송 실패", e);
                throw new RuntimeException("FCM 메시지 전송에 실패했습니다: " + token, e);
            }
        } catch (Exception e) {
            log.error("FCM 메시지 전송 실패", e);
            throw new RuntimeException("FCM 메시지 전송에 실패했습니다: " + token, e);
        }
    }
}
