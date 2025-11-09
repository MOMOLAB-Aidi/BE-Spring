package sw.momolab.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Turbidity {
    NONE("없음"),
    PRESENT("있음");

    private final String label;

    Turbidity(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
