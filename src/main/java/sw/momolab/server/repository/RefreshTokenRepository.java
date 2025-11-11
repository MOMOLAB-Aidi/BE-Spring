package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw.momolab.server.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}