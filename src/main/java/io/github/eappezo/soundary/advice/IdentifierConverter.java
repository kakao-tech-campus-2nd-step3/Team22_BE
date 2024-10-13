package io.github.eappezo.soundary.advice;

import io.github.eappezo.soundary.core.identification.Identifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class IdentifierConverter implements Converter<String, Identifier> {
    @Override
    public Identifier convert(@NonNull String value) {
        return Identifier.fromString(value);
    }
}
