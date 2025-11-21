package sw.momolab.server.service.tokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.AuthHandler;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.converter.TokenConverter;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.domain.RefreshToken;
import sw.momolab.server.domain.User;
import sw.momolab.server.domain.enums.UserStatus;
import sw.momolab.server.repository.RefreshTokenRepository;
import sw.momolab.server.util.TokenHasher;
import sw.momolab.server.service.userService.CustomUserDetailsService;
import sw.momolab.server.web.dto.TokenDTO.TokenRequestDTO;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommandServiceImpl implements RefreshTokenCommandService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenHasher tokenHasher;

    @Override
    public RefreshToken createRefreshToken(String refreshToken, User user) {
        // refreshToken 해시 생성
        String hashedRefreshToken = tokenHasher.hash(refreshToken);

        Date expiryDate = jwtTokenService.parseClaims(refreshToken).getExpiration();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());

        // 해시된 토큰을 DB에 저장
        RefreshToken refreshTokenEntity = TokenConverter.toRefreshTokenEntity(hashedRefreshToken, localDateTime, user);
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public TokenResponseDTO.AccessTokenDTO reissueToken(TokenRequestDTO.ReissueDTO reissueDTO) {
        if (reissueDTO == null || reissueDTO.getRefreshToken() == null || reissueDTO.getRefreshToken().isBlank()) {
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);
        }

        String clientRefreshToken = reissueDTO.getRefreshToken();

        // 1. 클라이언트가 보낸 평문 RT를 해시하여 DB 검색 키로 사용
        String hashedClientRT;
        try {
            hashedClientRT = tokenHasher.hash(clientRefreshToken);
        } catch (Exception e) {
            // 해싱 과정에서 실패하면 토큰 자체가 유효하지 않다고 간주
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);
        }

        // 2. JWT 서명 및 기본 클레임 검증 (클라이언트 rt 사용)
        try {
            jwtTokenService.parseClaims(clientRefreshToken);
        } catch (Exception e) {
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);
        }

        // 3. 해시된 토큰으로 엔티티 조회
        RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(hashedClientRT)
                .orElseThrow(() -> new AuthHandler(ErrorStatus.REFRESH_TOKEN_NOT_FOUND));

        if (storedRefreshToken.getUser() == null)
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);

        if (storedRefreshToken.getUser().getStatus() == UserStatus.INACTIVE)
            throw new UserHandler(ErrorStatus.USER_STATUS_INACTIVE);

        if (storedRefreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // 새로운 at 생성
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(storedRefreshToken.getUser().getLoginId());
        String accessToken = jwtTokenService.generateAccessToken(customUserDetails);

        return TokenConverter.toAccessTokenDTO(accessToken);
    }

    @Override
    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}