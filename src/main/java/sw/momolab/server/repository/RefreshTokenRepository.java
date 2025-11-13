package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}