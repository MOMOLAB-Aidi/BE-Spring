package sw.momolab.server.web.dto.AuthDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "로그인 요청 dto")
    public static class LoginRequestDTO {

        @Schema(description = "로그인할 계정의 아이디", example = "P12345678")
        @NotBlank(message = "필수 입력 항목입니다.")
        private String loginId;

        @Schema(description = "로그인할 계정의 비밀번호", example = "aidi2025")
        @NotBlank(message = "필수 입력 항목입니다.")
        private String password;
    }
}