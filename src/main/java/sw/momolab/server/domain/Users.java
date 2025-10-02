package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.Role;
import sw.momolab.server.domain.enums.UserStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메인 이메일
    @Column(unique = true, length = 50)
    private String primaryEmail;

    // 비밀번호
    @Column(length = 100)
    private String password;

    // 사용자 이름
    @Column(nullable = false, length = 20)
    private String name;

    // 사용자 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // 사용자 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
