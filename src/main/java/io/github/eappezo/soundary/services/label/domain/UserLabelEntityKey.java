package io.github.eappezo.soundary.services.label.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class UserLabelEntityKey implements Serializable {
    private String userId;
    private Label label;
}
