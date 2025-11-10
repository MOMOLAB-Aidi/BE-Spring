package sw.momolab.server.web.dto.PatientActivationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PatientActivationRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "환자 활성화 링크 생성 요청")
    public static class CreateActivationRequestDTO {
        @Schema(description = "환자 로그인 ID", example = "P12345678")
        @NotBlank(message = "loginId는 필수 입력입니다.")
        private String loginId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "환자 활성화 완료(비밀번호 설정) 요청")
    public static class CompleteActivationRequestDTO {
        @Schema(description = "활성화 토큰", example = "Q1w2E3r4T5y6...")
        @NotBlank(message = "token은 필수 입력입니다.")
        private String token;

        @Schema(description = "비밀번호(숫자 최소 1개 포함, 8~30자)", example = "momo2025")
        @NotBlank(message = "비밀번호는 필수 입력입니다.")
        @Size(min = 8, max = 30, message = "비밀번호는 8~30자여야 합니다.")
        private String password;
    }
}
