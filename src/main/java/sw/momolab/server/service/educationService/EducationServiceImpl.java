package sw.momolab.server.service.educationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.EducationHandler;
import sw.momolab.server.converter.EducationConverter;
import sw.momolab.server.domain.Education;
import sw.momolab.server.repository.EducationRepository;
import sw.momolab.server.web.dto.EducationDTO.EducationResponseDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Transactional(readOnly = true)
    @Override
    public EducationResponseDTO.TipResponseDTO getRandomTip() {
        Optional<Education> tips = educationRepository.findRandomActiveTip();
        if (tips.isEmpty()) {
            throw new EducationHandler(ErrorStatus.EDUCATION_NOT_FOUND);
        }

        Education education = tips.get();
        return EducationConverter.toEducationDTO(education);
    }
}
