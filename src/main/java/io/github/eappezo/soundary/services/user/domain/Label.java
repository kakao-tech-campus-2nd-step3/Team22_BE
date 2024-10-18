package io.github.eappezo.soundary.services.user.domain;

import io.github.eappezo.soundary.services.user.domain.exception.LabelInvalidValueException;

public enum Label {
    CLASSIC,
    JAZZ,
    ROCK,
    POP,
    HIPHOP,
    RNB,
    EDM;

    public static Label getLabel(String label){
        try{
            return Label.valueOf(label);
        }catch (IllegalArgumentException e){
            throw new LabelInvalidValueException();
        }
    }
}
