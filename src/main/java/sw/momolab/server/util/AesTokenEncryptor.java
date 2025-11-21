package sw.momolab.server.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AesTokenEncryptor {

    @Value("${token.aes-secret-key}")
    private String secretKey;
    private SecretKeySpec secretKeySpec;

    // 클래스 초기화 시 Cipher 인스턴스 설정
    @PostConstruct
    public void init() {
        // 비밀 키를 바이트 배열로 변환
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalStateException("token.aes-secret-key 는 16/24/32 바이트여야 합니다.");
        }
        // SecretKeySpec 생성
        this.secretKeySpec = new SecretKeySpec(keyBytes, "AES");
    }

    // 토큰 암호화
    public String encrypt(String text) {
        if (text == null) return null;
        try {
            // 매번 새로운 Cipher 인스턴스 생성 (스레드 세이프)
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);  // 저장된 키 스펙 사용
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Refresh Token 암호화 실패", e);
        }
    }
}
