package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientActivation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tokenHash; // 토큰 해시값

    @Column
    private LocalDateTime expiresAt; // 토큰 만료 시각

    @Column
    private LocalDateTime usedAt; // 토큰 사용 시각

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public void updateUsedAt(LocalDateTime now) {
        this.usedAt = now;
    }
}
