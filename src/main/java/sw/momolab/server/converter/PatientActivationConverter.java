package sw.momolab.server.converter;

import sw.momolab.server.domain.Patient;
import sw.momolab.server.domain.PatientActivation;
import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationResponseDTO;

public class PatientActivationConverter {

    public static PatientActivationResponseDTO.CompleteActivationResponseDTO toCompleteActivationDTO(Patient patient, PatientActivation patientActivation) {
        return PatientActivationResponseDTO.CompleteActivationResponseDTO.builder()
                .loginId(patient.getLoginId())
                .password(patient.getPassword())
                .usedAt(patientActivation.getUsedAt())
                .lastLoginAt(patient.getLastLoginAt())
                .build();
    }
}
