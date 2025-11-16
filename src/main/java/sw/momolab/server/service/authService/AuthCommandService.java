package sw.momolab.server.service.authService;

import sw.momolab.server.domain.User;
import sw.momolab.server.web.dto.AuthDTO.AuthRequestDTO;
import sw.momolab.server.web.dto.AuthDTO.AuthResponseDTO;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

public interface AuthCommandService {
    AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.LoginRequestDTO request);
    TokenResponseDTO.TokenDTO performAuthentication(String loginId, String password);
    void setRefreshToken(String refreshToken, User user);
    void logout(Long userId);
}