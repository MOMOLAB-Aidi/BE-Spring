package sw.momolab.server.service.fcmService;

public interface FcmNotificationService {
    void sendToToken(String token, String title, String body);
}
