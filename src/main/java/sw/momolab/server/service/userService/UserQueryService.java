package sw.momolab.server.service.userService;

import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

public interface UserQueryService {
    String getRecordDate(Long userId);
    UserResponseDTO.MyPageDTO getMyPage(Long userId);
}
