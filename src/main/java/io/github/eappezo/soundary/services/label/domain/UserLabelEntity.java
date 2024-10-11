package io.github.eappezo.soundary.services.label.domain;

import io.github.eappezo.soundary.core.persistence.infrastructure.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
