package sw.momolab.server.service.refreshTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw.momolab.server.converter.TokenConverter;
import sw.momolab.server.domain.RefreshToken;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.RefreshTokenRepository;
import sw.momolab.server.util.JwtTokenUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommandServiceImpl implements RefreshTokenCommandService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public RefreshToken createRefreshToken(String refreshToken, User user) {
        Date expiryDate = jwtTokenUtil.parseClaims(refreshToken).getExpiration();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());

        RefreshToken refreshTokenEntity = TokenConverter.toRefreshTokenResponseDTO(refreshToken, localDateTime, user);

        return refreshTokenRepository.save(refreshTokenEntity);
    }
}