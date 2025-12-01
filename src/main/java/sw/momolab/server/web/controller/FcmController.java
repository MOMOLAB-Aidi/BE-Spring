package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sw.momolab.server.service.fcmService.FcmTokenService;
import sw.momolab.server.web.dto.FcmDTO.FcmRequestDTO;

@Tag(name = "fcm", description = "FCM 토큰 및 알림 관련 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("/token")
    @Operation(summary = "FCM 토큰 등록", description = "로그인한 사용자의 FCM 토큰을 서버에 등록합니다.")
    public ResponseEntity<Void> registerToken(@RequestBody @Valid FcmRequestDTO.FcmTokenRequestDTO request) {
        fcmTokenService.registerToken(request.getFcmToken());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/token")
    @Operation(summary = "FCM 토큰 비활성화", description = "클라이언트 로그아웃 시 사용자의 FCM 토큰을 비활성화합니다.")
    public ResponseEntity<Void> deactivateToken(@RequestBody @Valid FcmRequestDTO.FcmTokenRequestDTO request) {
        fcmTokenService.deactivateToken(request.getFcmToken());
        return ResponseEntity.ok().build();
    }
}