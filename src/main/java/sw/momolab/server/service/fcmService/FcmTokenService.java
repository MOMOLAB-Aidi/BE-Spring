package sw.momolab.server.service.fcmService;

import sw.momolab.server.web.dto.FcmDTO.FcmRequestDTO;

public interface FcmTokenService {
    void registerToken(Long userId, FcmRequestDTO.FcmTokenRequestDTO request);
    void deactivateToken(Long userId, FcmRequestDTO.FcmTokenRequestDTO request);
    void deactivateByToken(String token);

}