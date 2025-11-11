package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalTime;

@Table(name = "record_exchange")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordExchange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구분(회차)
    @Column(nullable = false)
    private Integer exchangeNo;

    // 교환 시각
    @Column(nullable = false)
    private LocalTime exchangeTime;

    // 배액량
    @Column(nullable = false)
    private Integer drainVolume;

    // 주입량
    @Column(nullable = false)
    private Integer fillVolume;

    // 주입액 농도
    @Column(nullable = false)
    private BigDecimal fillConcentration;

    // 제수량
    @Column(nullable = false)
    private Integer uf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;
}