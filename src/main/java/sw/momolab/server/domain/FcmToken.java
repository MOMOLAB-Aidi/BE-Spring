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

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    private Boolean isActive;

    public void activate() {
        this.isActive = true;
        this.registeredAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
    }
}
