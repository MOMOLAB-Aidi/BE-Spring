package sw.momolab.server.service.patientActivationService;

import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationResponseDTO;

public interface PatientActivationService {
    PatientActivationResponseDTO.CreateActivationResponseDTO createActivation(String loginId);
    void completeActivation(String token, String password);
}
