package sw.momolab.server.converter;

import sw.momolab.server.domain.Education;
import sw.momolab.server.web.dto.EducationDTO.EducationResponseDTO;

public class EducationConverter {

    public static EducationResponseDTO.TipResponseDTO toEducationDTO(Education education) {
        return EducationResponseDTO.TipResponseDTO.builder()
                .tipId(education.getId())
                .body(education.getMessageKo())
                .build();
    }
}
