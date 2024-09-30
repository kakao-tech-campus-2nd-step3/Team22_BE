package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class APIAuthentication implements Authentication {
    private final Identifier userId;
    private final String accessToken;
    private final List<GrantedAuthority> roles;
    private Boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getCredentials() {
        return accessToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userId.toString();
    }

    public static APIAuthentication of(
            Identifier userId,
            String accessToken,
            List<UserRole> roles
    ) {
        List<GrantedAuthority> grantedAuthorities = roles
                .stream()
                .map((role) -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.name())))
                .collect(Collectors.toUnmodifiableList());
        return new APIAuthentication(
                userId,
                accessToken,
                grantedAuthorities
        );
    }
}
