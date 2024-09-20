package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.authentication.application.TokenProvider;
import io.github.eappezo.soundary.services.authentication.infrastructure.security.APIAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {
    private final JwtProperties jwtProperties;
    private final JwtClaimsProvider jwtClaimsProvider;
    private final JwtTokenExtractor jwtTokenExtractor;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), "HmacSHA512");
    }

    @Override
    public String generateAccessTokenFrom(User user) {
        return Jwts.builder()
                .claims(jwtClaimsProvider.buildClaims(user))
                .issuedAt(now())
                .expiration(buildAccessTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshTokenFrom(User user) {
        return Jwts.builder()
                .claims(jwtClaimsProvider.buildClaims(user))
                .issuedAt(now())
                .expiration(buildRefreshTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        Identifier userId = jwtClaimsProvider.extractUserId(claims);
        List<UserRole> roles = jwtClaimsProvider.extractRoles(claims);
        return Jwts.builder()
                .claims(jwtClaimsProvider.buildClaims(userId, roles))
                .issuedAt(now())
                .expiration(buildAccessTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Long getAccessTokenExpirationTime() {
        return jwtProperties.getAccessTokenExpiration();
    }

    private Long getRefreshTokenExpirationTime() {
        return jwtProperties.getRefreshTokenExpiration();
    }

    private Date buildAccessTokenExpiration() {
        return new Date(System.currentTimeMillis() + getAccessTokenExpirationTime());
    }

    private Date buildRefreshTokenExpiration() {
        return new Date(System.currentTimeMillis() + getRefreshTokenExpirationTime());
    }

    private Date now() {
        return new Date(System.currentTimeMillis());
    }
}
