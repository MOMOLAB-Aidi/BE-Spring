package sw.momolab.server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenUtil {
    @Value("${security.token.pepper}")
    private String pepper;

    private final SecureRandom random = new SecureRandom();

    public String generateUrlSafeToken(int bytes) {
        byte[] b = new byte[bytes];
        random.nextBytes(b);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }

    public String hashWithPepper(String token) {
        MessageDigest md;
        try { md = MessageDigest.getInstance("SHA-256"); }
        catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
        md.update(pepper.getBytes(StandardCharsets.UTF_8));
        md.update(token.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(md.digest());
    }
}

