package io.github.eappezo.soundary.core.user;

import io.github.eappezo.soundary.core.identification.Identifier;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
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

    public User updateUserInfo(
            @Nullable String nickname,
            @Nullable String description,
            @Nullable String profileImageUrl
    ){
        return new User(
                this.identifier,
                this.displayId,
                nickname != null ? nickname : this.nickname,
                description != null ? description : this.description,
                profileImageUrl != null ? profileImageUrl : this.profileImageUrl,
                this.roles,
                this.signupAt
        );
    }

    public UserRole getPrimaryRole() {
        return this.roles.stream()
                .max(Comparator.comparingInt(UserRole::getPriority))
                .orElseThrow(IllegalStateException::new);  // roles가 비어있을 경우 기본값을 반환
    }
}
