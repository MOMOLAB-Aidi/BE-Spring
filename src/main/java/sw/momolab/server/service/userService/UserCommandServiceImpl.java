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
    public void resetPassword(Long userId, UserRequestDTO.ResetPasswordDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        if (user.getStatus() == UserStatus.INACTIVE)
            throw new UserHandler(ErrorStatus.USER_STATUS_INACTIVE);

        // 비밀번호 변경사항 없을 때 예외 처리
        String rawPassword = request.getPassword();
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserHandler(ErrorStatus.PASSWORD_UPDATE_NO_CHANGE);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.encodePassword(hashedPassword); //사용자 비밀번호 변경
    }
}
