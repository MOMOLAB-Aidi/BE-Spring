package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.domain.Education;
import sw.momolab.server.service.educationService.EducationService;
import sw.momolab.server.web.dto.EducationDTO.EducationResponseDTO;

@Tag(name = "education", description = "교육용 팁 관련 API")
@RestController
@RequestMapping("/api/v1/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/today-tip")
    @Operation(summary = "오늘의 복막투석 TIP 추출 API", description = "education 테이블에서 복막투석 관리 TIP을 랜덤으로 1개 추출합니다.")
    public ResponseEntity<EducationResponseDTO.TipResponseDTO> getTodayTip() {
        Education education = educationService.getRandomTip();
        EducationResponseDTO.TipResponseDTO response = new EducationResponseDTO.TipResponseDTO(
                education.getId(),
                "오늘의 복막투석 관리 TIP",
                education.getMessageKo()
        );
        return ResponseEntity.ok(response);
    }
}