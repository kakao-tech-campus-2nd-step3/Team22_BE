package io.github.eappezo.soundary.services.label.domain;

import io.github.eappezo.soundary.services.label.domain.exception.LabelInvalidValueException;

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
