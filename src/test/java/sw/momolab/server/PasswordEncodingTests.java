package sw.momolab.server;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncodingTests {

    @Test
    void generateEncodedPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 직접 객체 생성

        String rawPassword = "aidi2025";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("==================================");
        System.out.println("암호화된 BCrypt 비밀번호: " + encodedPassword);
        System.out.println("==================================");
    }
}