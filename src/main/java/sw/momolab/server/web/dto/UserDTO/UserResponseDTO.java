package sw.momolab.server.web.dto.UserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "마이페이지 응답 dto")
    public static class MyPageDTO {
        @Schema(description = "환자 ID", example = "P12345678")
        private String loginId;

        @Schema(description = "투석 시작일")
        private LocalDate recordStartDate;

        @Schema(description = "기록 일지를 작성해온 기간", example = "D+15")
        private String recordPeriod;

        @Schema(description = "마지막으로 로그인한 날짜")
        private LocalDateTime lastLoginAt;
    }
}
