package sw.momolab.server.service.patientActivationService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.PatientActivationHandler;
import sw.momolab.server.apiPayload.exception.PatientHandler;
import sw.momolab.server.domain.Patient;
import sw.momolab.server.domain.PatientActivation;
import sw.momolab.server.repository.PatientActivationRepository;
import sw.momolab.server.repository.PatientRepository;
import sw.momolab.server.util.TokenUtil;
import sw.momolab.server.web.dto.PatientActivationDTO.PatientActivationResponseDTO;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientActivationServiceImpl implements PatientActivationService {

    private static final Duration ACTIVATION_TTL = Duration.ofMinutes(3);

    private final PatientRepository patientRepository;
    private final PatientActivationRepository activationRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;

    @Value("${app.activation.base-url}")
    private String activationBaseUrl;

    @Override
    public PatientActivationResponseDTO.CreateActivationResponseDTO createActivation(String loginId) {
        LocalDateTime now = LocalDateTime.now();

        Patient patient = patientRepository.findByLoginId(loginId)
                .orElseThrow(() -> new PatientHandler(ErrorStatus.PATIENT_NOT_FOUND));

        // 환자당 유효 토큰 1개로 제한
        if (activationRepository.countActiveByPatient(patient.getId(), now) > 0) {
            throw new PatientActivationHandler(ErrorStatus.VALID_TOKEN_ALREADY_EXISTS);
        }

        String token = tokenUtil.generateUrlSafeToken(48); // 환자에게 전달할 평문
        String tokenHash = tokenUtil.hashWithPepper(token); // DB 저장용 해시
        LocalDateTime expiresAt = now.plus(ACTIVATION_TTL);

        PatientActivation patientActivation = PatientActivation.builder()
                .patient(patient)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .build();

        activationRepository.save(patientActivation);

        // 환자에게 보낼 URL
        String url = activationBaseUrl + "?token=" + token;

        return PatientActivationResponseDTO.CreateActivationResponseDTO.builder()
                .activationUrl(url)
                .expiresAt(expiresAt)
                .build();
    }

    @Override
    public PatientActivationResponseDTO.CompleteActivationResponseDTO completeActivation(String token, String password) {
        LocalDateTime now = LocalDateTime.now();

        String tokenHash = tokenUtil.hashWithPepper(token);
        PatientActivation patientActivation = activationRepository.findValidByHash(tokenHash, now)
                .orElseThrow(() -> new PatientActivationHandler(ErrorStatus.INVALID_TOKEN));

        Patient patient = patientActivation.getPatient();

        validatePassword(password);
        patient.encodePassword(passwordEncoder.encode(password));

        patientActivation.updateUsedAt(now);
        patient.updateLastLoginAt(now);

        return PatientActivationResponseDTO.CompleteActivationResponseDTO.builder()
                .loginId(patient.getLoginId())
                .password(patient.getPassword())
                .usedAt(patientActivation.getUsedAt())
                .lastLoginAt(patient.getLastLoginAt())
                .build();
    }

    private void validatePassword(String password) {
        if (!password.matches(".*\\d.*")) {
            throw new PatientHandler(ErrorStatus.PASSWORD_MUST_INCLUDE_NUMBER);
        }
    }
}
