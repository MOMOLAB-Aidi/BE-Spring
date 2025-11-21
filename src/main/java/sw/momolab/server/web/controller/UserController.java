package sw.momolab.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sw.momolab.server.apiPayload.ApiResponse;
import sw.momolab.server.service.userService.UserCommandService;
import sw.momolab.server.service.userService.UserQueryService;
import sw.momolab.server.web.dto.UserDTO.UserRequestDTO;
import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

@Tag(name = "users", description = "사용자 관련 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회 API", description = "사용자의 아이디와 최초 기록 날짜, 기록을 작성해온 기간을 조회합니다.")
    public ApiResponse<UserResponseDTO.MyPageDTO> getMyPage(@AuthenticationPrincipal(expression = "id") Long userId) {
        UserResponseDTO.MyPageDTO result = userQueryService.getMyPage(userId);
        return ApiResponse.onSuccess(result);
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 재설정 API", description = "로그인한 사용자의 비밀번호를 재설정합니다.")
    public ApiResponse<Void> resetPassword(@AuthenticationPrincipal(expression = "id") Long userId, @RequestBody UserRequestDTO.ResetPasswordDTO request) {
        userCommandService.resetPassword(userId, request);
        return ApiResponse.onSuccess();
    }
}