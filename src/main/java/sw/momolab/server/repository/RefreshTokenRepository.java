package sw.momolab.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw.momolab.server.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.id = :refreshTokenId")
    void deleteById(@Param("refreshTokenId") Long refreshTokenId);
}