package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.UserRole;
import sw.momolab.server.domain.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 아이디
    @Column(unique = true, length = 50, nullable = false)
    private String loginId;

    // 사용자 비밀번호
    @Column(length = 100)
    private String password;

    // 사용자가 마지막에 로그인한 날짜와 시각
    @Column
    private LocalDateTime lastLoginAt;

    // 사용자 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // 사용자 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();

    public void encodePassword(String password) {
        this.password = password;
    }

    public void updateLastLoginAt(LocalDateTime now) {
        this.lastLoginAt = now;
    }

    public void setRefreshToken(RefreshToken refreshTokenEntity) {
        this.refreshToken = refreshTokenEntity;
    }
}
