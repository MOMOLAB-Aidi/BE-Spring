package sw.momolab.server.service.userService;

import sw.momolab.server.web.dto.UserDTO.UserRequestDTO;

public interface UserCommandService {
    void resetPassword(Long userId, UserRequestDTO.ResetPasswordDTO request);
}
