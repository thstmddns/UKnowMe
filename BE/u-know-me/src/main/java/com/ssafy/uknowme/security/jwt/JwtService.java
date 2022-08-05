package com.ssafy.uknowme.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private final long expireTime = 1000 * 30;

    // 로그인서비스와 함께
    public Map<String, String> createTokenSet(String id) {

        Map<String, String> tokens = new HashMap<>();

        String accessToken = getAccessToken(id);
        String refreshToken = getRefreshToken(id);

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public String getAccessToken(String id) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key keySpec = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(id)
                .signWith(keySpec, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public String getRefreshToken(String id) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Key keySpec = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

       return Jwts.builder()
                .setSubject(id)
                .signWith(keySpec, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 6 * 24))
                .compact();
    }

    // 토큰 컴증하는 메서드를 boolean
    public String getSubject(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimsResolver(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaims(token));
    }

    public Date getExpirationDate(String token) {
        return getClaimsResolver(token, Claims::getExpiration);
    }
}
