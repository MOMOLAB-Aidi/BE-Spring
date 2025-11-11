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
import sw.momolab.server.service.authService.AuthCommandService;
import sw.momolab.server.web.dto.AuthDTO.AuthRequestDTO;
import sw.momolab.server.web.dto.AuthDTO.AuthResponseDTO;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping("/login")
    @Operation(summary = "로그인 인증 API", description = "등록한 계정의 아이디와 비밀번호가 일치하면 로그인에 성공합니다.")
    public ApiResponse<AuthResponseDTO.LoginResponseDTO> login(@RequestBody @Valid AuthRequestDTO.LoginRequestDTO request) {
        AuthResponseDTO.LoginResponseDTO result = authCommandService.login(request);
        return ApiResponse.onSuccess(result);
    }
}