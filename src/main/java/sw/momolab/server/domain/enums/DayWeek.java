package sw.momolab.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.time.DayOfWeek;

public enum DayWeek {
    MON("월", DayOfWeek.MONDAY),
    TUE("화", DayOfWeek.TUESDAY),
    WED("수", DayOfWeek.WEDNESDAY),
    THU("목", DayOfWeek.THURSDAY),
    FRI("금", DayOfWeek.FRIDAY),
    SAT("토", DayOfWeek.SATURDAY),
    SUN("일", DayOfWeek.SUNDAY);

    private final String label;
    private final DayOfWeek dow;

    DayWeek(String label, DayOfWeek dow) {
        this.label = label;
        this.dow = dow;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
