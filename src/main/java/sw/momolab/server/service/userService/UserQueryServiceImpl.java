package sw.momolab.server.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.converter.UserConverter;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.RecordRepository;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.web.dto.AuthDTO.AuthRequestDTO;
import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Override
    public String getRecordDate(Long userId) {
        // 사용자가 처음으로 일기를 작성한 날짜 조회
        LocalDate firstRecordDate = recordRepository.findFirstRecordDateByUserId(userId)
                .orElse(LocalDate.now()); // 작성 기록이 없으면 오늘 날짜를 기본값으로 사용

        // 오늘 날짜와의 차이를 계산
        long days = ChronoUnit.DAYS.between(firstRecordDate, LocalDate.now());

        return "D+" + days;
    }

    @Override
    public UserResponseDTO.MyPageDTO getMyPage(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 사용자가 처음으로 일기를 작성한 날짜 조회
        LocalDate firstRecordDate = recordRepository.findFirstRecordDateByUserId(userId)
                .orElse(LocalDate.now()); // 작성 기록이 없으면 오늘 날짜를 기본값으로 사용

        String dPlusPeriod = getRecordDate(userId); // 기록을 작성해온 기간

        return UserConverter.toMyPageDTO(user, firstRecordDate, dPlusPeriod);
    }
}
