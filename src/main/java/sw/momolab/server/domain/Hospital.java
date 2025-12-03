package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw.momolab.server.domain.common.BaseEntity;

@Entity
@Table(name = "hospital")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내부 병원 코드 (예: CNUH_MAIN)
    @Column(nullable = false, unique = true, length = 32)
    private String code;

    // 병원 이름 (예: 전남대학교병원)
    @Column(nullable = false, length = 100)
    private String name;

    // 응급실 번호
    @Column(name = "emergency_phone", length = 20)
    private String emergencyPhone;

    // 주소
    @Column
    private String address;

    // 시/군 단위
    @Column(length = 50)
    private String city;

    // 도/광역시 단위
    @Column(length = 50)
    private String province;

    // 사용 여부
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}