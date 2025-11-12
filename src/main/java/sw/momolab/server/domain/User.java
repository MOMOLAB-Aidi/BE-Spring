package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;
import sw.momolab.server.domain.enums.UserRole;
import sw.momolab.server.domain.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디(고유번호)
    @Column(unique = true, length = 50, nullable = false)
    private String loginId;

    // 비밀번호
    @Column(length = 100, nullable = false)
    private String password;

    // 마지막에 로그인한 날짜와 시각
    @Column
    private LocalDateTime lastLoginAt;

    // 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();

    public void updateLastLoginAt(LocalDateTime now) {
        this.lastLoginAt = now;
    }

    public void setRefreshToken(RefreshToken refreshTokenEntity) {
        this.refreshToken = refreshTokenEntity;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }
}