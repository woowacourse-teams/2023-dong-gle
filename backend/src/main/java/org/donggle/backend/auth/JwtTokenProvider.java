package org.donggle.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.donggle.backend.auth.exception.NoSuchTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String MEMBER_ID_KEY = "memberId";

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.access-token-expire-length}") final int accessTokenValidityInMilliseconds,
                            @Value("${security.jwt.token.refresh-token-expire-length}") final int refreshTokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public String createAccessToken(final Long payload) {
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(final Long payload) {
        return createToken(payload, refreshTokenValidityInMilliseconds);
    }

    private String createToken(final Long payload, final long validityInMilliseconds) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim(MEMBER_ID_KEY, payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getPayload(final String token) {
        return getClaims(token).getBody().get(MEMBER_ID_KEY, Long.class);
    }

    public boolean inValidTokenUsage(final String token) {
        try {
            final Jws<Claims> claims = getClaims(token);
            return claims.getBody().getExpiration().before(new Date());
        } catch (final ExpiredJwtException e) {
            throw new NoSuchTokenException();
        } catch (final JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    private Jws<Claims> getClaims(final String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
