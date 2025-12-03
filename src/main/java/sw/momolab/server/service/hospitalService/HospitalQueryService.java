package sw.momolab.server.service.hospitalService;

import sw.momolab.server.web.dto.HospitalDTO.HospitalResponseDTO;

public interface HospitalQueryService {
    HospitalResponseDTO.HospitalInfoDTO getHospitalInfo(Long userId);
}