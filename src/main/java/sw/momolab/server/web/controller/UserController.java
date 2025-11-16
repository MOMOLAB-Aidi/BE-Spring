package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.momolab.server.apiPayload.ApiResponse;
import sw.momolab.server.service.userService.UserQueryService;
import sw.momolab.server.web.dto.AuthDTO.AuthRequestDTO;
import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

@Tag(name = "users", description = "사용자 관련 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회 API", description = "사용자의 아이디와 최초 기록 날짜, 기록을 작성해온 기간을 조회합니다.")
    public ApiResponse<UserResponseDTO.MyPageDTO> getMyPage(@AuthenticationPrincipal(expression = "id") Long userId) {
        UserResponseDTO.MyPageDTO result = userQueryService.getMyPage(userId);
        return ApiResponse.onSuccess(result);
    }
}
