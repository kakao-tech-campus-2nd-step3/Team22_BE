package io.github.eappezo.soundary.core.persistence.infrastructure;

import io.github.eappezo.soundary.core.user.Label;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_labels")
@IdClass(UserLabelEntityKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserLabelEntity extends BaseEntity {
    @Id
    private String userId;

    @Id
    @Enumerated(EnumType.STRING)
    private Label label;

}
