package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();
}
