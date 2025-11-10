package sw.momolab.server.web.dto.PatientActivationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PatientActivationResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "환자 활성화 링크 생성 응답")
    public static class CreateActivationResponseDTO {
        @Schema(description = "환자에게 전달할 활성화 URL", example = "https://app.example.com/activate?token=xxxx")
        private String activationUrl;

        @Schema(description = "토큰 만료 시각", example = "2025-11-10T12:00:00")
        private LocalDateTime expiresAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "환자 비밀번호 설정 요청 응답")
    public static class CompleteActivationResponseDTO {
        @Schema(description = "환자의 로그인 ID", example = "P12345678")
        private String loginId;

        @Schema(description = "환자가 설정한 비밀번호", example = "momo2025")
        private String password;

        @Schema(description = "토큰을 사용한 시각")
        private LocalDateTime usedAt;

        @Schema(description = "마지막으로 로그인한 시각")
        private LocalDateTime lastLoginAt;
    }
}
