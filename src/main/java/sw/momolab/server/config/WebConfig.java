package sw.momolab.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public WebConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CorsProperties에서 허용 origin 문자열 가져오기
        String allowed = corsProperties.allowedOrigins();

        if (allowed != null && !allowed.isBlank()) {
            var patterns = Arrays.stream(allowed.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .toList();

            if (!patterns.isEmpty()) {
                // 허용할 origin 패턴
                config.setAllowedOriginPatterns(patterns);

                // 허용할 HTTP 메서드
                config.setAllowedMethods(List.of("POST", "GET", "PATCH", "DELETE"));

                // 허용할 헤더
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

                // 인증 정보 허용
                config.setAllowCredentials(true);

                // 모든 경로에 적용
                source.registerCorsConfiguration("/**", config);
            }
        }

        return source;
    }
}
