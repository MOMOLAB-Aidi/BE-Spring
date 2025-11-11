package sw.momolab.server.service.tokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import sw.momolab.server.converter.TokenConverter;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenService {
    private final SecretKey key;
    private final long ACCESS_TOKEN_EXPIRATION_MS;
    private final long REFRESH_TOKEN_EXPIRATION_MS;

    public JwtTokenService(Environment env) {

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

    // at, rt 발급
    public TokenResponseDTO.TokenDTO generateToken(CustomUserDetails customUserDetails) {
        String accessToken = generateAccessToken(customUserDetails);
        String refreshToken = generateRefreshToken(customUserDetails);

        return TokenConverter.toTokenResponseDTO(accessToken, refreshToken);
    }

    public String generateAccessToken(CustomUserDetails customUserDetails) {
        String authorities = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // at 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRATION_MS);

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim("roles", authorities)
                .claim("userId", customUserDetails.getId())
                .expiration(accessTokenExpiresIn)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(CustomUserDetails customUserDetails) {
        long now = (new Date()).getTime();

        // rt 생성
        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .expiration(new Date(now + REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    // jwt 토큰에서 클레임 추출, 만료된 토큰에 대해서는 예외를 그대로 전파
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}