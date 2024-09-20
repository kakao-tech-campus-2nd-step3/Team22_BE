package io.github.eappezo.soundary.core.identification;

public class Identifier {
    private final String value;

    private Identifier(String value) {
        this.value = value;
    }

    public static Identifier fromString(String value) {
        return new Identifier(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
