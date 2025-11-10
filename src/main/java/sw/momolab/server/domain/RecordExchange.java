package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;

import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordExchange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer exchange_no; // 구분(회차)

    @Column(nullable = false)
    private LocalTime exchange_time; // 교환 시각

    @Column(nullable = false)
    private Integer drain_volume; // 배액량

    @Column(nullable = false)
    private Integer fill_volume; // 주입량

    @Column(nullable = false)
    private Float fill_concentration; // 주입액 농도

    @Column(nullable = false)
    private Integer uf; // 제수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;
}
