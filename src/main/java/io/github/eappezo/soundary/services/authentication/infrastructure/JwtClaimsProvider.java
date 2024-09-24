package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtClaimsProvider {
    private static final String USER_ID_KEY = "userId";
    private static final String ROLES_KEY = "roles";
    private static final String ROLE_SEPARATOR = "::";

    public Map<String, Object> buildClaims(User user) {
        return Map.of(
                USER_ID_KEY, user.getIdentifier().toString(),
                ROLES_KEY, collectRoles(user)
        );
    }

    public Map<String, Object> buildClaims(Identifier userId, List<UserRole> roles) {
        return Map.of(
                USER_ID_KEY, userId.toString(),
                ROLES_KEY, collectRoles(roles)
        );
    }

    public Identifier extractUserId(Map<String, Object> claims) {
        return Identifier.fromString(claims.get(USER_ID_KEY).toString());
    }

    public List<UserRole> extractRoles(Map<String, Object> claims) {
        return Stream.of(claims.get(ROLES_KEY).toString().split(ROLE_SEPARATOR))
                .map(UserRole::valueOf)
                .toList();
    }

    private String collectRoles(User user) {
        return collectRoles(user.getRoles());
    }

    private String collectRoles(List<UserRole> roles) {
        return roles.stream().map(Enum::name).collect(Collectors.joining(ROLE_SEPARATOR));
    }
}
