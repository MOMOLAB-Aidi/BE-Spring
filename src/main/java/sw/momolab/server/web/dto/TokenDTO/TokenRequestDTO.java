package sw.momolab.server.web.dto.TokenDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "토큰 재발급 요청 dto")
    public static class ReissueDTO {
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJ...")
        @NotBlank(message = "리프레시 토큰은 필수 입력 항목입니다.")
        private String refreshToken;
    }
}
