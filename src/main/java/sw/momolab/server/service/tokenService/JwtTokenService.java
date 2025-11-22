package sw.momolab.server.service.tokenService;

import io.jsonwebtoken.Claims;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

public interface JwtTokenService {
    TokenResponseDTO.TokenDTO generateToken(CustomUserDetails customUserDetails);
    String generateAccessToken(CustomUserDetails customUserDetails);
    String generateRefreshToken(CustomUserDetails customUserDetails);
    String generateTestAccessToken(CustomUserDetails customUserDetails);
    Claims parseClaims(String token);
}
