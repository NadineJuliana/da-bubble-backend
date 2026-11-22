package da_bubble.backend.config.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Two kinds of tokens:
 * - access token:  short-lived (15 min), sent with every request as "Authorization: Bearer ..."
 * - refresh token: long-lived (1 day), only used at /auth/refresh to get a new access token
 * The "type" claim tells them apart, so a refresh token can't be used as an access token and vice versa.
 */
@Component
public class JwtUtil {

    private static final String TYPE_CLAIM = "type";
    private static final String FIRST_NAME_CLAIM = "firstName";
    private static final String LAST_NAME_CLAIM = "lastName";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    private final SecretKey key;
    private final Duration accessTokenValidity;
    private final Duration refreshTokenValidity;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.access-token-validity}") Duration accessTokenValidity,
                   @Value("${jwt.refresh-token-validity}") Duration refreshTokenValidity) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String generateAccessToken(String email, String firstName, String lastName) {
        return tokenBuilder(email, ACCESS, accessTokenValidity)
                .claim(FIRST_NAME_CLAIM, firstName)
                .claim(LAST_NAME_CLAIM, lastName)
                .compact();
    }

    // The refresh token only needs the email: on /auth/refresh the user is loaded from the DB anyway
    public String generateRefreshToken(String email) {
        return tokenBuilder(email, REFRESH, refreshTokenValidity)
                .compact();
    }

    private JwtBuilder tokenBuilder(String email, String type, Duration validity) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(email)
                .claim(TYPE_CLAIM, type)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(validity)))
                .signWith(key);
    }

    public boolean isValidAccessToken(String token) {
        return hasType(token, ACCESS);
    }

    public boolean isValidRefreshToken(String token) {
        return hasType(token, REFRESH);
    }

    // Valid = correct signature, not expired, and of the expected type
    private boolean hasType(String token, String expectedType) {
        try {
            Claims claims = parseClaims(token);
            return expectedType.equals(claims.get(TYPE_CLAIM, String.class));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
