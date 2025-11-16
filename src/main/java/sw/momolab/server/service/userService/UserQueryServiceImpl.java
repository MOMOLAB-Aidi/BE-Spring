package sw.momolab.server.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.UserHandler;
import sw.momolab.server.converter.UserConverter;
import sw.momolab.server.domain.User;
import sw.momolab.server.repository.RecordRepository;
import sw.momolab.server.repository.UserRepository;
import sw.momolab.server.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO.MyPageDTO getMyPage(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 사용자가 처음으로 일기를 작성한 날짜 조회
        Optional<LocalDate> firstRecordDateOpt = recordRepository.findFirstRecordDateByUserId(userId);

        LocalDate firstRecordDate;
        String dPlusPeriod;

        if (firstRecordDateOpt.isPresent()) {
            firstRecordDate = firstRecordDateOpt.get();
            long days = ChronoUnit.DAYS.between(firstRecordDate, LocalDate.now());
            dPlusPeriod = "D+" + days;
        } else {
            firstRecordDate = null;
            dPlusPeriod = "기록 없음";
        }

        return UserConverter.toMyPageDTO(user, firstRecordDate, dPlusPeriod);
    }
}
