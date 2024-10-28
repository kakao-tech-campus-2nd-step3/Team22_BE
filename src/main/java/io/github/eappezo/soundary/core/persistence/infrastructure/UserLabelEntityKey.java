package io.github.eappezo.soundary.core.persistence.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UserLabelEntityKey implements Serializable {
    private String userId;
    private Label label;

    public static UserLabelEntityKey of(Identifier userId, Label label) {
        return new UserLabelEntityKey(userId.toString(), label);
    }
}
