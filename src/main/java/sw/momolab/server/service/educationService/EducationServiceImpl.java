package sw.momolab.server.service.educationService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.domain.Education;
import sw.momolab.server.repository.EducationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Transactional(readOnly = true)
    public Education getRandomTip() {
        List<Education> tips = educationRepository.findRandomActiveTips(PageRequest.of(0, 1));
        if (tips.isEmpty()) {
            throw new IllegalStateException("사용 가능한 교육 문구가 없습니다.");
        }
        return tips.get(0);
    }
}
