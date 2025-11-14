package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.apiPayload.ApiResponse;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.service.recordService.RecordQueryService;
import sw.momolab.server.web.dto.RecordDTO.RecordResponseDTO;

import java.time.YearMonth;
import java.util.List;

@Tag(name = "records", description = "기록 관련 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/records")
public class RecordController {

    private final RecordQueryService recordQueryService;

    @GetMapping("/calendar")
    @Operation(summary = "캘린더 조회 API", description = "환자의 기록 여부를 캘린더에서 조회합니다.")
    public ApiResponse<List<RecordResponseDTO.CalendarResponseDTO>> getMonthlyCalendar(@AuthenticationPrincipal CustomUserDetails user,
                                                                                       @Parameter(description = "기록 연도", example = "2025")@RequestParam @Min(2000) @Max(2100) int year,
                                                                                       @Parameter(description = "기록 달", example = "11")@RequestParam @Min(1) @Max(12) int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        Long userId = user.getId();

        List<RecordResponseDTO.CalendarResponseDTO> result = recordQueryService.getMonthlyRecordStatus(userId, yearMonth);
        return ApiResponse.onSuccess(result);
    }
}