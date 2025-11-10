package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.apiPayload.ApiResponse;
import sw.momolab.server.service.patientActivationService.PatientActivationService;
import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationRequestDTO;
import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationResponseDTO;

@Tag(name = "Patient", description = "환자 관련 API")
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientActivationController {

    private final PatientActivationService patientActivationService;

    @PostMapping("/activation")
    @Operation(summary = "활성화 URL 발급 API", description = "EMR 측에서 비밀번호를 설정할 수 있는 활성화 URL을 제공합니다.")
    public ApiResponse<PatientActivationResponseDTO.CreateActivationResponseDTO> createActivation(@Valid @RequestBody PatientActivationRequestDTO.CreateActivationRequestDTO request) {
        PatientActivationResponseDTO.CreateActivationResponseDTO response = patientActivationService.createActivation(request.getLoginId());
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/activation/complete")
    @Operation(summary = "초기 비밀번호 설정 API", description = "토큰값을 사용하여 환자의 초기 비밀번호를 설정합니다.")
    public ApiResponse<Void> completeActivation(@Valid @RequestBody PatientActivationRequestDTO.CompleteActivationRequestDTO request) {
        patientActivationService.completeActivation(request.getToken(), request.getPassword());
        return ApiResponse.onSuccess();
    }
}

