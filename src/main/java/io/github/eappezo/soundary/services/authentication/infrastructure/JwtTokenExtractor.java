package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.authentication.infrastructure.security.APIAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {
    private final JwtProperties jwtProperties;
    private final JwtClaimsProvider jwtClaimsProvider;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), "HmacSHA512");
    }

    public APIAuthentication extractAuthenticationFrom(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Identifier userId = jwtClaimsProvider.extractUserId(claims);
        List<UserRole> roles = jwtClaimsProvider.extractRoles(claims);

        return APIAuthentication.of(userId, token, roles);
    }
}
