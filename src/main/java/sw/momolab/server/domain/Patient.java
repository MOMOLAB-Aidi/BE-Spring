package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.Gender;
import sw.momolab.server.domain.enums.Role;
import sw.momolab.server.domain.enums.UserStatus;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(nullable = false, length = 20)
    private String name;

    // 휴대폰번호
    @Column(nullable = false, length = 11)
    private String phoneNum;

    // 생년월일
    @Column(nullable = false, length = 6)
    private LocalDate birth;

    // 성별
    @Column(nullable = false)
    private Gender gender;

    // 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
