package io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key;

import io.github.eappezo.soundary.services.user.domain.Label;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UserLabelEntityKey implements Serializable {
    private String userId;
    private Label label;
}
