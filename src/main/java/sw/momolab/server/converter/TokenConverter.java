package sw.momolab.server.converter;

import sw.momolab.server.domain.RefreshToken;
import sw.momolab.server.domain.User;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

import java.time.LocalDateTime;

public class TokenConverter {
    public static RefreshToken toRefreshTokenDTO(String refreshToken, LocalDateTime expiryDate, User user) {
        return RefreshToken.builder()
                .refreshToken(refreshToken)
                .expiryDate(expiryDate)
                .user(user)
                .build();
    }

    public static TokenResponseDTO.AccessTokenDTO toAccessTokenDTO(String accessToken) {
        return TokenResponseDTO.AccessTokenDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    public static TokenResponseDTO.TokenDTO toTokenDTO(String accessToken, String refreshToken){
        return TokenResponseDTO.TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}