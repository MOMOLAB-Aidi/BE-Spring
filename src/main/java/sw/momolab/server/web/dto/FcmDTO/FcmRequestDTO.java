package sw.momolab.server.web.dto.FcmDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FcmRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "FCM 토큰 등록 요청 dto")
    public static class FcmTokenRequestDTO {
        @NotBlank(message = "FCM 토큰은 비어 있을 수 없습니다.")
        @Schema(description = "디바이스에서 발급받은 FCM 토큰")
        private String fcmToken;
    }
}
