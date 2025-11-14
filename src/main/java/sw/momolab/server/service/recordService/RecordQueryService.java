package sw.momolab.server.service.recordService;

import sw.momolab.server.web.dto.RecordDTO.RecordResponseDTO;

import java.time.YearMonth;
import java.util.List;

public interface RecordQueryService {
    List<RecordResponseDTO.CalendarResponseDTO> getMonthlyRecordStatus(Long userId, YearMonth yearMonth);
}
