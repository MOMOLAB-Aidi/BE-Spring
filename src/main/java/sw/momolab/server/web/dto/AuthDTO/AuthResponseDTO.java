package sw.momolab.server.web.dto.AuthDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw.momolab.server.web.dto.TokenDTO.TokenResponseDTO;

public class AuthResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "로그인 응답 dto")
    public static class LoginResponseDTO {
        @Schema(description = "발급받은 토큰 목록")
        private TokenResponseDTO.TokenDTO tokens;
    }
}