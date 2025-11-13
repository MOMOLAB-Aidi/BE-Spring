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

    @Override
    public RefreshToken createRefreshToken(String refreshToken, User user) {
        Date expiryDate = jwtTokenService.parseClaims(refreshToken).getExpiration();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());

        RefreshToken refreshTokenEntity = TokenConverter.toRefreshTokenEntity(refreshToken, localDateTime, user);
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public TokenResponseDTO.AccessTokenDTO reissueToken(TokenRequestDTO.ReissueDTO reissueDTO) {
        if (reissueDTO == null || reissueDTO.getRefreshToken() == null || reissueDTO.getRefreshToken().isBlank()) {
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);
        }

        RefreshToken storedRefreshToken = refreshTokenRepository.findByRefreshToken(reissueDTO.getRefreshToken())
                .orElseThrow(() -> new AuthHandler(ErrorStatus.REFRESH_TOKEN_NOT_FOUND));

        if (storedRefreshToken.getUser() == null)
            throw new AuthHandler(ErrorStatus.TOKEN_INVALID);

        if (storedRefreshToken.getUser().getStatus() == UserStatus.INACTIVE)
            throw new UserHandler(ErrorStatus.USER_STATUS_INACTIVE);

        if (storedRefreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(storedRefreshToken.getUser().getLoginId());
        String accessToken = jwtTokenService.generateAccessToken(customUserDetails); // at 생성

        return TokenConverter.toAccessTokenDTO(accessToken);
    }
}