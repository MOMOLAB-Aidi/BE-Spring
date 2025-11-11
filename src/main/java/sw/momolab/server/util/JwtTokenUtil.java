package sw.momolab.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.AuthHandler;
import sw.momolab.server.converter.TokenConverter;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    private final SecretKey key;

    @Value("${jwt.access-token-expiration-ms}")
    private long ACCESS_TOKEN_EXPIRATION_MS;
    @Value("${jwt.refresh-token-expiration-ms}")
    private long REFRESH_TOKEN_EXPIRATION_MS;

    public JwtTokenUtil(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        // 키 길이 검증
        if (keyBytes.length < 32) {
            throw new AuthHandler(ErrorStatus.SECRET_KEY_TOO_SHORT);
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