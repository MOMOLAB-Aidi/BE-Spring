package sw.momolab.server.service.fcmService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.domain.FcmToken;
import sw.momolab.server.repository.FcmTokenRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    // 토큰 등록/갱신
    @Override
    public void registerToken(String token) {
        FcmToken fcmToken = fcmTokenRepository.findByToken(token)
                .orElseGet(() -> FcmToken.builder()
                        .token(token)
                        .registeredAt(LocalDateTime.now())
                        .isActive(true)
                        .build()
                );

        // 기존에 있던 경우 비활성화 상태면 다시 활성화
        fcmToken.activate();
        fcmTokenRepository.save(fcmToken);
    }

    // 토큰 비활성화
    @Override
    public void deactivateToken(String token) {
        fcmTokenRepository.findByToken(token)
                .ifPresent(FcmToken::deactivate);
    }
}
