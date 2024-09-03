package com.example.gamgyulman.domain.member.jwt;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.JwtErrorCode;
import com.example.gamgyulman.domain.member.exception.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private static final String ACCESS_TYPE = "ACCESS";
    private static final String REFRESH_TYPE = "REFRESH";
    private SecretKey secret;
    private long accessExpiration;
    private long refreshExpiration;

    public JwtProvider(@Value("${Jwt.secret}") String secret, @Value("${Jwt.token.access-expiration-time}") long accessExpiration, @Value("${Jwt.token.refresh-expiration-time}") long refreshExpiration) {
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String createAccessToken(Member member) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(this.accessExpiration);
        return Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .subject(member.getEmail())
                .claim("id", member.getId())
                .claim("type", ACCESS_TYPE)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiredAt))
                .signWith(secret)
                .compact();
    }

    public String createRefreshToken(Member member) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(this.refreshExpiration);
        return Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .subject(member.getEmail())
                .claim("id", member.getId())
                .claim("type", REFRESH_TYPE)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiredAt))
                .signWith(secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            return claims.getPayload().getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage() + ": 토큰이 유효하지 않습니다.");
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return isCorrectType(ACCESS_TYPE, token);
    }

    public boolean isRefreshToken(String token) {
        return isCorrectType(REFRESH_TYPE, token);
    }

    public Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new JwtException(JwtErrorCode.INVALID_TOKEN);
        }
    }

    public String getEmail(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    private boolean isCorrectType(String type, String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            String tokenType = claims.getPayload().get("type", String.class);
            if (!tokenType.equals(type)) {
                throw new JwtException(JwtErrorCode.NOT_ACCESS_TOKEN);
            }
            return true;
        } catch (JwtException e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
