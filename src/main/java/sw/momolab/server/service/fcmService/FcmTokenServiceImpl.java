package sw.momolab.server.service.fcmService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.domain.FcmToken;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.FcmTokenRepository;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.web.dto.FcmDTO.FcmRequestDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;

    // 토큰 등록/갱신
    @Override
    public void registerToken(Long userId, FcmRequestDTO.FcmTokenRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        String token = request.getFcmToken();
        FcmToken fcmToken = fcmTokenRepository.findByToken(token)
                .orElseGet(() -> FcmToken.builder()
                        .token(token)
                        .user(user)
                        .lastActivatedAt(LocalDateTime.now())
                        .isActive(false)
                        .build()
                );

        fcmToken.activate();
        fcmTokenRepository.save(fcmToken);
    }

    // 토큰 비활성화
    @Override
    public void deactivateToken(Long userId, FcmRequestDTO.FcmTokenRequestDTO request) {
        String token = request.getFcmToken();
        FcmToken fcmToken = fcmTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("토큰을 찾을 수 없습니다."));

        if (!fcmToken.getUser().getId().equals(userId)) {
            throw new SecurityException("본인의 토큰만 비활성화할 수 있습니다.");
        }

       fcmToken.deactivate();
    }

    // FCM 서버에서 "이 토큰 죽었음"이라고 알려줄 때 사용하는 내부용 메소드
    @Override
    public void deactivateByToken(String token) {
        fcmTokenRepository.findByToken(token)
                .ifPresent(FcmToken::deactivate);
    }
}