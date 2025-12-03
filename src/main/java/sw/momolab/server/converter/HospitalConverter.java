package sw.momolab.server.converter;

import sw.momolab.server.domain.Hospital;
import sw.momolab.server.web.dto.HospitalDTO.HospitalResponseDTO;

public class HospitalConverter {
    public static HospitalResponseDTO.HospitalInfoDTO toHospitalDTO(Hospital hospital) {
        return HospitalResponseDTO.HospitalInfoDTO.builder()
                .name(hospital.getName())
                .emergencyPhone(hospital.getEmergencyPhone())
                .build();
    }
}
