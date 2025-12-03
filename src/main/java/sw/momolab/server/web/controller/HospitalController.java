package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.apiPayload.ApiResponse;
import sw.momolab.server.service.hospitalService.HospitalQueryService;
import sw.momolab.server.web.dto.HospitalDTO.HospitalResponseDTO;

@Tag(name = "hospital", description = "병원 관련 API")
@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalQueryService hospitalQueryService;

    @GetMapping("")
    @Operation(summary = "자주 가는 병원 조회 API", description = "환자가 자주 가는 병원을 조회합니다.")
    public ApiResponse<HospitalResponseDTO.HospitalInfoDTO> getHospitalInfo(@AuthenticationPrincipal(expression = "id") Long userId) {
        HospitalResponseDTO.HospitalInfoDTO response = hospitalQueryService.getHospitalInfo(userId);
        return ApiResponse.onSuccess(response);
    }
}