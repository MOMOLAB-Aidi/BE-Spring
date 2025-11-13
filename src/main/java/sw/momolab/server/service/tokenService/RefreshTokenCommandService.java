package sw.momolab.server.service.tokenService;

import sw.momolab.server.domain.User;
import sw.momolab.server.domain.RefreshToken;
import sw.momolab.server.web.dto.TokenDTO.TokenRequestDTO;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

public interface RefreshTokenCommandService {
    RefreshToken createRefreshToken(String refreshToken, User user);
    TokenResponseDTO.AccessTokenDTO reissueToken(TokenRequestDTO.ReissueDTO reissueDTO);
}