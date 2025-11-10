package sw.momolab.server.web.dto.PatientActivationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
