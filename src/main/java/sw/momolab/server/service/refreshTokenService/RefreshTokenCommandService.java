package sw.momolab.server.service.refreshTokenService;

import sw.momolab.server.domain.User;
import sw.momolab.server.domain.RefreshToken;

public interface RefreshTokenCommandService {
    RefreshToken createRefreshToken(String refreshToken, User patient);
}