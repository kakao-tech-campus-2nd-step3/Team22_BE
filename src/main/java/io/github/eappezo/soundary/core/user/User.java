package io.github.eappezo.soundary.core.user;

import io.github.eappezo.soundary.core.identification.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class User {
    private final Identifier identifier;
    private final String displayId;
    private final String nickname;
    private final String description;
    private final String profileImageUrl;
    private final List<UserRole> roles;
    private final LocalDateTime signupAt;

    public static User newUser(
            Identifier identifier,
            String displayId,
            String nickname,
            String description,
            String profileImageUrl
    ) {
        return new User(
                identifier,
                displayId,
                nickname,
                description,
                profileImageUrl,
                List.of(UserRole.PENDING),
                LocalDateTime.now()
        );
    }
}
