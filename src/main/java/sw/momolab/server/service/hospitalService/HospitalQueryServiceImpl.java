package sw.momolab.server.service.hospitalService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.HospitalHandler;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.converter.HospitalConverter;
import sw.momolab.server.domain.Hospital;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.web.dto.HospitalDTO.HospitalResponseDTO;

@Service
@RequiredArgsConstructor
public class HospitalQueryServiceImpl implements HospitalQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public HospitalResponseDTO.HospitalInfoDTO getHospitalInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        Hospital hospital = user.getHospital();
        if (hospital == null) {
            throw new HospitalHandler(ErrorStatus.HOSPITAL_NOT_FOUND);
        }

        return HospitalConverter.toHospitalDTO(hospital);
    }
}