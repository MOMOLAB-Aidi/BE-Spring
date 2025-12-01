package sw.momolab.server.service.fcmService;

public interface FcmTokenService {
    void registerToken(String token);
    void deactivateToken(String token);
}