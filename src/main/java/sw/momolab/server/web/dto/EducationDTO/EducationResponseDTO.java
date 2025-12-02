package sw.momolab.server.web.dto.EducationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EducationResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "교육 팁 응답 dto")
    public static class TipResponseDTO {
        @Schema(description = "팁 아이디", example = "61")
        private Long tipId;
        @Schema(description = "제목", example = "오늘의 복막투석 관리 TIP")
        private String title;
        @Schema(description = "팁 내용", example = "평소보다 몸이 붓거나 발목이 잘 눌리면, 최근 체중과 소변·배액량을 함께 기록해 두고 의료진과 상의해 주세요.")
        private String body;
    }
}
