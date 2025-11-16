package sw.momolab.server.service.authService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.AuthHandler;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.converter.AuthConverter;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.domain.RefreshToken;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.service.tokenService.JwtTokenService;
import sw.momolab.server.service.tokenService.RefreshTokenCommandService;
import sw.momolab.server.web.dto.AuthDTO.AuthRequestDTO;
import sw.momolab.server.web.dto.AuthDTO.AuthResponseDTO;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService {

    private final UserRepository userRepository;

    private final RefreshTokenCommandService refreshTokenCommandService;

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.LoginRequestDTO request) {
        String loginId = request.getLoginId();
        String password = request.getPassword();

        // 사용자가 존재하지 않는 경우
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new AuthHandler(ErrorStatus.INVALID_CREDENTIALS));

        try {
            // 인증 수행 (비밀번호 검증)
            TokenResponseDTO.TokenDTO tokenDTO = performAuthentication(loginId, password);

            // 인증 성공 후 로직
            setRefreshToken(tokenDTO.getRefreshToken(), user);
            user.updateLastLoginAt(LocalDateTime.now());

            return AuthConverter.toLoginResponseDTO(tokenDTO);
        } catch (BadCredentialsException e) {
            // 비밀번호가 일치하지 않는 경우
            throw new AuthHandler(ErrorStatus.INVALID_CREDENTIALS);
        }
    }

    @Override
    public void setRefreshToken(String refreshToken, User user) {
        if (user.getRefreshToken() != null) {
            user.deleteRefreshToken();
        }

        entityManager.flush(); // delete 쿼리를 즉시 실행

        RefreshToken refreshTokenEntity = refreshTokenCommandService.createRefreshToken(refreshToken, user);
        user.setRefreshToken(refreshTokenEntity);
    }

    @Override
    public TokenResponseDTO.TokenDTO performAuthentication(String loginId, String password) {
        //인증되지 않은 상태의 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, password);

        //인증 성공 시 인증된 상태의 Authentication 객체 반환, 인증 실패 시 예외 던짐
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //인증 성공 시 JWT 토큰 생성
        return jwtTokenService.generateToken((CustomUserDetails) authentication.getPrincipal());
    }

    @Override
    @Transactional
    public void logout(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // RefreshToken이 있는 경우에만 삭제 처리
        if (user.getRefreshToken() != null) {
            RefreshToken refreshToken = user.getRefreshToken();
            user.deleteRefreshToken();
            refreshTokenCommandService.deleteRefreshToken(refreshToken);
        }
    }
}