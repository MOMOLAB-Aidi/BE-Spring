package sw.momolab.server.converter;

import sw.momolab.server.domain.User;
import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDate;

public class UserConverter {
    public static UserResponseDTO.MyPageDTO toMyPageDTO(User user, LocalDate firstRecordDate, String dPlusPeriod) {
        return UserResponseDTO.MyPageDTO.builder()
                .loginId(user.getLoginId())
                .recordStartDate(firstRecordDate)
                .recordPeriod(dPlusPeriod)
                .build();
    }
}
