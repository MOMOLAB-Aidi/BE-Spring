package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.DayWeek;
import sw.momolab.server.domain.enums.Turbidity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate record_date; // 기록 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayWeek record_dw; // 기록 요일

    @Column(nullable = false)
    private Float weight; // 체중

    @Column(nullable = false)
    private Integer systolic; // 최고 혈압

    @Column(nullable = false)
    private Integer diastolic; // 최저 혈압

    @Column(nullable = false)
    private Integer fasting_glucose; // 공복 혈당

    @Column(nullable = false)
    private Integer urine_count; // 소변 횟수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turbidity turbidity; // 복막액 혼탁

    @Column(nullable = false)
    private Integer total_uf; // 제수량 합계

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes; // 비고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordExchange> recordExchangeList = new ArrayList<>();
}
