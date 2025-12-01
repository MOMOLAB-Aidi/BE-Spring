package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.service.fcmService.FcmTokenService;
import sw.momolab.server.web.dto.FcmDTO.FcmRequestDTO;

@Tag(name = "fcm", description = "fcm API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("/token")
    public ResponseEntity<Void> registerToken(@RequestBody FcmRequestDTO.FcmTokenRequestDTO request) {
        fcmTokenService.registerToken(request.getFcmToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/deactivate")
    public ResponseEntity<Void> deactivateToken(@RequestBody FcmRequestDTO.FcmTokenRequestDTO request) {
        fcmTokenService.deactivateToken(request.getFcmToken());
        return ResponseEntity.ok().build();
    }
}