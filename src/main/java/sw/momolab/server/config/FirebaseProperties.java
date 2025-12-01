package sw.momolab.server.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "firebase.config")
public class FirebaseProperties {
    @NotBlank(message = "Firebase 인증 파일 경로는 필수입니다.")
    private String path;
}