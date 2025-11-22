package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.domain.CustomUserDetails;
import sw.momolab.server.service.tokenService.JwtTokenService;

import java.util.Map;

@Tag(name = "test", description = "테스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final JwtTokenService jwtTokenService;

    @GetMapping("/token")
    @Operation(summary = "테스트용 토큰 발급 API", description = "테스트용 토큰을 발급합니다.")
    public Map<String, String> getTestToken(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String token = jwtTokenService.generateTestAccessToken(userDetails);
        return Map.of("accessToken", token);
    }
}
