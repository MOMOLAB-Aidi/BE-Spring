package sw.momolab.server.service.userService;

import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

public interface UserQueryService {
    UserResponseDTO.MyPageDTO getMyPage(Long userId);
}
