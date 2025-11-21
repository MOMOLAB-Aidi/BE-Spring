package sw.momolab.server.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AesTokenEncryptor {

    @Value("${token.aes-secret-key}")
    private String secretKey;
    private SecretKeySpec macKeySpec;

    // 클래스 초기화 시 AES 키 스펙 설정
    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("token.aes-secret-key 설정이 비어 있습니다.");
        }

        // 비밀 키를 바이트 배열로 변환
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalStateException("token.aes-secret-key 는 16/24/32 바이트여야 합니다.");
        }

        // MacKeySpec 생성
        this.macKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // 토큰 HMAC(일방향) 생성
    public String encrypt(String text) {
        if (text == null) return null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(macKeySpec);
            byte[] hmac = mac.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Refresh Token 암호화 실패", e);
        }
    }
}
