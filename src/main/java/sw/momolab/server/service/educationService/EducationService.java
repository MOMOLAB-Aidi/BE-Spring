package sw.momolab.server.service.educationService;

import sw.momolab.server.web.dto.EducationDTO.EducationResponseDTO;

public interface EducationService {
    EducationResponseDTO.TipResponseDTO getRandomTip();
}
