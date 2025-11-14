package sw.momolab.server.service.recordService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.momolab.server.converter.RecordConverter;
import sw.momolab.server.repository.RecordRepository;
import sw.momolab.server.web.dto.RecordDTO.RecordResponseDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordQueryServiceImpl implements RecordQueryService {

    private final RecordRepository recordRepository;

    @Override
    public List<RecordResponseDTO.CalendarResponseDTO> getMonthlyRecordStatus(Long userId, YearMonth yearMonth) {

        // 해당 월의 시작 날짜와 끝 날짜를 계산
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        Set<LocalDate> recordedDates = recordRepository.findDistinctRecordDatesByUserIdAndDateRange(userId, startDate, endDate);
        Set<String> recordedDateStrings = recordedDates.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toSet());

        // 해당 월의 모든 날짜를 순회하며 DTO를 생성
        int daysInMonth = yearMonth.lengthOfMonth();

        return IntStream.rangeClosed(1, daysInMonth)
                .mapToObj(day -> {
                    LocalDate date = yearMonth.atDay(day);
                    // 기록이 존재하는지 확인
                    boolean hasSchedule = recordedDateStrings.contains(date.toString());

                    return RecordConverter.toCalendarResponseDTO(date, hasSchedule);
                })
                .collect(Collectors.toList());
    }
}
