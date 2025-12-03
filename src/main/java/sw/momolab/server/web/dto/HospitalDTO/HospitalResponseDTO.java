package sw.momolab.server.web.dto.HospitalDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HospitalResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(title = "자주 가는 병원 조회 응답 dto")
    public static class HospitalInfoDTO {
        @Schema(description = "병원 이름", example = "전남대학교병원")
        private String name;

        @Schema(description = "응급실 번호", example = "062-220-5555")
        private String emergencyPhone;
    }
}