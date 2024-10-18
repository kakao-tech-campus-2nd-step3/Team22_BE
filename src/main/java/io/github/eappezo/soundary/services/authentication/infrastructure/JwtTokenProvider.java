package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.authentication.application.TokenPayloadDto;
import io.github.eappezo.soundary.services.authentication.domain.TokenProvider;
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
    public String generateAccessToken(TokenPayloadDto payload) {
        return Jwts.builder()
                .claims(jwtClaimsProvider.buildClaims(payload.userId(), payload.roles()))
                .issuedAt(now())
                .expiration(buildAccessTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(TokenPayloadDto payload) {
        return Jwts.builder()
                .claims(jwtClaimsProvider.buildClaims(payload.userId(), payload.roles()))
                .issuedAt(now())
                .expiration(buildRefreshTokenExpiration())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public TokenPayloadDto extractPayloadFrom(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Identifier userId = jwtClaimsProvider.extractUserId(claims);
        List<UserRole> roles = jwtClaimsProvider.extractRoles(claims);
        return new TokenPayloadDto(userId, roles);
    }

    @Override
    public Long getAccessTokenExpirationTime() {
        return jwtProperties.getAccessTokenExpiration();
    }

    private Long getRefreshTokenExpirationTime() {
        return jwtProperties.getRefreshTokenExpiration();
    }

    private Date buildAccessTokenExpiration() {
        return new Date(System.currentTimeMillis() + getAccessTokenExpirationTime() * 1000);
    }

    private Date buildRefreshTokenExpiration() {
        return new Date(System.currentTimeMillis() + getRefreshTokenExpirationTime() * 1000);
    }

    private Date now() {
        return new Date(System.currentTimeMillis());
    }
}
