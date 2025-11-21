package sw.momolab.server.service.cryptoService;

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
    private Cipher encryptCipher;

    // 클래스 초기화 시 Cipher 인스턴스 설정
    @PostConstruct
    public void init() throws Exception {
        // 비밀 키를 바이트 배열로 변환
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        // SecretKeySpec 생성
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Cipher 인스턴스 초기화 (AES/ECB/PKCS5Padding 사용)
        // ECB 모드는 보안에 취약할 수 있으므로, 실제 서비스에서는 CBC 또는 GCM 모드를 권장하며, 이 경우 IV(Initialization Vector) 관리가 필요
        this.encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Cipher를 암호화 모드와 복호화 모드로 초기화
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    }

    // 토큰 암호화
    public String encrypt(String text) {
        if (text == null) return null;
        try {
            byte[] encrypted = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            // Base64로 인코딩하여 문자열로 반환
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Refresh Token 암호화 실패", e);
        }
    }
}
