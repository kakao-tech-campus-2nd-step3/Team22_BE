package io.github.eappezo.soundary.advice;

import com.github.f4b6a3.ulid.UlidCreator;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.identification.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class ULIDGenerator implements IdentifierGenerator {
    @Override
    public Identifier generate() {
        return Identifier.fromString(UlidCreator.getMonotonicUlid().toString());
    }
}
