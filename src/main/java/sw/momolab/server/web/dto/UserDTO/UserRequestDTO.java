package sw.momolab.server.web.dto.UserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "비밀번호 재설정 요청 dto")
    public static class ResetPasswordDTO {

        @Schema(description = "변경하려는 비밀번호 입력", example = "aidi2025")
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^(?=.*\\d).{8,30}$", message = "비밀번호는 8~30자 이내이며, 최소 1개의 숫자를 포함해야 합니다.")
        private String password;

        @Schema(description = "변경하려는 비밀번호 재입력", example = "aidi2025")
        @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
        private String passwordCheck;
    }
}
