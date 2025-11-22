package sw.momolab.server.domain;

import jakarta.persistence.*;
import lombok.*;
import sw.momolab.server.domain.common.BaseEntity;

@Table(name = "kdigo_chunks")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KdigoChunks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // KDIGO 텍스트 청크
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    // 임베딩 벡터: PostgreSQL의 jsonb 컬럼에 매핑
    @Column(nullable = false, columnDefinition = "jsonb")
    private String embedding;
}