package sw.momolab.server.converter;

import sw.momolab.server.web.dto.RecordDTO.RecordResponseDTO;

import java.time.LocalDate;

public class RecordConverter {
    public static RecordResponseDTO.CalendarResponseDTO toCalendarResponseDTO(LocalDate date, boolean hasSchedule) {
        return RecordResponseDTO.CalendarResponseDTO.builder()
                .date(date)
                .hasSchedule(hasSchedule)
                .build();
    }
}