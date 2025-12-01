package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.FcmToken;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByToken(String token);

    // 활성 토큰만 조회
    List<FcmToken> findAllByIsActiveTrue();
}
