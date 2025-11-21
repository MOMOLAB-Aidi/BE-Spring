package sw.momolab.server.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TokenHasher {

    @Value("${token.hmac-secret-key}")
    private String secretKey;
    private SecretKeySpec macKeySpec;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("token.hmac-secret-key 설정이 비어 있습니다.");
        }

        // 비밀 키를 바이트 배열로 변환
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalStateException("token.hmac-secret-key는 최소 32바이트 이상이어야 합니다.");
        }

        // MacKeySpec 생성
        this.macKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // 토큰 HMAC 해시 생성
    public String hash(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("해시할 텍스트는 null이거나 비어있을 수 없습니다.");
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(macKeySpec);
            byte[] hmac = mac.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Refresh Token 해시 생성 실패", e);
        }
    }
}
