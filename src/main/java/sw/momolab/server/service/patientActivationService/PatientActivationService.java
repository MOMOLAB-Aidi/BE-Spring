package sw.momolab.server.service.patientActivationService;

import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationResponseDTO;

public interface PatientActivationService {
    PatientActivationResponseDTO.CreateActivationResponseDTO createActivation(String loginId);
    PatientActivationResponseDTO.CompleteActivationResponseDTO completeActivation(String token, String password);
}
