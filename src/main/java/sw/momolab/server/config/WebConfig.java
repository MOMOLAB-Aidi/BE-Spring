package sw.momolab.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        if (!allowedOrigins.isEmpty()) {
            // 허용할 origin
            config.setAllowedOriginPatterns(List.of(
                    "http://localhost:8080",
                    "https://momolab-spring-704930097925.asia-northeast3.run.app"
            ));

            // 허용할 HTTP 메서드
            config.setAllowedMethods(List.of(
                    "POST",
                    "GET",
                    "PATCH",
                    "DELETE"
            ));

            // 허용할 헤더
            config.setAllowedHeaders(List.of(
                    "Authorization",
                    "Content-Type"
            ));

            // 인증 정보 허용
            config.setAllowCredentials(true);

            // 모든 경로에 적용
            source.registerCorsConfiguration("/**", config);
        }

        return source;
    }
}
