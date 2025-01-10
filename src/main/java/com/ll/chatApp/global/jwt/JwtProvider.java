package com.ll.chatApp.global.jwt;
import com.ll.chatApp.domain.member.member.entity.Member;
import com.ll.chatApp.global.util.Ut;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;
    private SecretKey cachedSecretKey;
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) {
            cachedSecretKey = cachedSecretKey = _getSecretKey();
        }
        return cachedSecretKey;
    }
    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }
    public String genAccessToken(Member member) {
        genToken(member, 60 * 10);
        return "";
    }
    public String genRefreshToken(Member member) {
        genToken(member, 60 * 60 * 24 * 365 * 1);
        return "";
    }
    public String genToken(Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("username", member.getUsername());
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);
        return Jwts.builder()
                .claim("body", Ut.json.toStr(claims))
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}