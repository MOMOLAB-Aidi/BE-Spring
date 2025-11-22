package sw.momolab.server.service.tokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import sw.momolab.server.converter.TokenConverter;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private final SecretKey key;
    private final long ACCESS_TOKEN_EXPIRATION_MS;
    private final long REFRESH_TOKEN_EXPIRATION_MS;

    private static final long TEST_ACCESS_TOKEN_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 30; // 30일

    @Autowired
    public JwtTokenServiceImpl(Environment env) {

        // Environment를 사용하여 프로퍼티 값을 읽어옴
        String secretKey = env.getRequiredProperty("JWT_SECRET_KEY");
        this.ACCESS_TOKEN_EXPIRATION_MS = env.getRequiredProperty("JWT_ACCESS_TOKEN_EXPIRATION_MS", Long.class);
        this.REFRESH_TOKEN_EXPIRATION_MS = env.getRequiredProperty("JWT_REFRESH_TOKEN_EXPIRATION_MS", Long.class);

        // 키 길이 검증 및 초기화
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret key는 최소 256비트여야 합니다.");
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public TokenResponseDTO.TokenDTO generateToken(CustomUserDetails customUserDetails) {
        String accessToken = generateAccessToken(customUserDetails);
        String refreshToken = generateRefreshToken(customUserDetails);

        return TokenConverter.toTokenDTO(accessToken, refreshToken);
    }

    @Override
    public String generateAccessToken(CustomUserDetails customUserDetails) {
        return buildAccessToken(customUserDetails, ACCESS_TOKEN_EXPIRATION_MS);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails customUserDetails) {
        long now = (new Date()).getTime();

        // rt 생성
        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .expiration(new Date(now + REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    // 테스트용 accessToken
    @Override
    public String generateTestAccessToken(CustomUserDetails customUserDetails) {
        return buildAccessToken(customUserDetails, TEST_ACCESS_TOKEN_EXPIRATION_MS);
    }

    // jwt 토큰에서 클레임 추출, 만료된 토큰에 대해서는 예외를 그대로 전파
    @Override
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // CustomUserDetails에서 권한 목록을 콤마로 이어붙인 문자열로 변환
    private String extractAuthorities(CustomUserDetails customUserDetails) {
        return customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // 만료 시간을 받아 at를 생성하는 공통 메서드
    private String buildAccessToken(CustomUserDetails customUserDetails, long expirationMs) {
        String authorities = extractAuthorities(customUserDetails);
        long now = System.currentTimeMillis();
        Date expiresAt = new Date(now + expirationMs);

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim("roles", authorities)
                .claim("userId", customUserDetails.getId())
                .expiration(expiresAt)
                .signWith(key)
                .compact();
    }
}