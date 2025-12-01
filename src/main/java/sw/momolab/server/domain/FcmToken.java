package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "fcm_token")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 512)
    private String token;

    @Column
    private LocalDateTime lastActivatedAt;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FcmToken)) return false;
        FcmToken fcmToken = (FcmToken) o;
        return token != null && token.equals(fcmToken.token);
    }

    public void activate() {
        this.isActive = true;
        this.lastActivatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
    }
}
