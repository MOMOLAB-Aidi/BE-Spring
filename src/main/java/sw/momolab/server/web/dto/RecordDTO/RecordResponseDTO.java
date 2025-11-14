package sw.momolab.server.web.dto.RecordDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class RecordResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @Schema(title = "캘린더 응답 dto")
    public static class CalendarResponseDTO{

        @Schema(description = "날짜")
        private LocalDate date;

        @Schema(description = "기록 여부", example = "true")
        private Boolean hasSchedule;
    }
}
