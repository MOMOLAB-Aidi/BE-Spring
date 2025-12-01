package sw.momolab.server.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.domain.User;
import sw.momolab.server.domain.enums.UserStatus;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.web.dto.UserDTO.UserRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(Long userId, UserRequestDTO.ResetPasswordDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        if (user.getStatus() == UserStatus.INACTIVE)
            throw new UserHandler(ErrorStatus.USER_STATUS_INACTIVE);

        // 1. 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.INVALID_CURRENT_PASSWORD);
        }

        // 2. 새 비밀번호 / 새 비밀번호 확인 일치 여부
        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new UserHandler(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 3. 새 비밀번호가 기존 비밀번호와 동일한지 검사
        String newRawPassword = request.getNewPassword();
        if (passwordEncoder.matches(newRawPassword, user.getPassword())) {
            throw new UserHandler(ErrorStatus.PASSWORD_UPDATE_NO_CHANGE);
        }

        String hashedPassword = passwordEncoder.encode(newRawPassword);
        user.encodePassword(hashedPassword); //사용자 비밀번호 변경
    }
}