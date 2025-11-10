package sw.momolab.server.service.patientActivationService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                .orElseThrow(() -> new IllegalArgumentException("환자를 찾을 수 없습니다."));

        // 환자당 유효 토큰 1개로 제한
        if (activationRepository.countActiveByPatient(patient.getId(), now) > 0) {
            throw new IllegalStateException("유효한 토큰이 이미 존재합니다.");
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
        PatientActivation pa = activationRepository.findValidByHash(tokenHash, now)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다."));

        Patient p = pa.getPatient();

        validatePassword(password);
        p.encodePassword(passwordEncoder.encode(password));

        pa.updateUsedAt(now);
        p.updateLastLoginAt(now);

        return PatientActivationResponseDTO.CompleteActivationResponseDTO.builder()
                .loginId(p.getLoginId())
                .password(p.getPassword())
                .usedAt(pa.getUsedAt())
                .lastLoginAt(p.getLastLoginAt())
                .build();
    }

    private void validatePassword(String pw) {
        if (pw == null || pw.length() < 8 || pw.length() > 30) {
            throw new IllegalArgumentException("비밀번호는 8~30자여야 합니다.");
        }
        if (!pw.matches(".*\\d.*")) {
            throw new IllegalArgumentException("비밀번호에는 최소 한 개의 숫자가 포함되어야 합니다.");
        }
    }
}
