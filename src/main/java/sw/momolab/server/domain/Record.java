package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.DayWeek;
import sw.momolab.server.domain.enums.Turbidity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "record")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기록 날짜
    @Column(nullable = false)
    private LocalDate recordDate;

    // 기록 요일
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayWeek recordDw;

    // 체중
    @Column(nullable = false)
    private Float weight;

    // 최고 혈압
    @Column(nullable = false)
    private Integer systolic;

    // 최저 혈압
    @Column(nullable = false)
    private Integer diastolic;

    // 공복 혈당
    @Column(nullable = false)
    private Integer fastingGlucose;

    // 소변 횟수
    @Column(nullable = false)
    private Integer urineCount;

    // 복막액 혼탁
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turbidity turbidity;

    // 제수량 합계
    @Column(nullable = false)
    private Integer totalUf;

    // 비고 (선택)
    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes;

    // GCS에 저장된 OCR 이미지 파일 경로
    @Column
    private String gcsPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordExchange> recordExchangeList = new ArrayList<>();
}