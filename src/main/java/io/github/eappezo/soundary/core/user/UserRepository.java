package io.github.eappezo.soundary.core.user;

import io.github.eappezo.soundary.core.identification.Identifier;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Identifier userId);

    Optional<User> findByDisplayId(String displayId);

    Optional<Identifier> findIdByDisplayId(String displayId);

    boolean existsById(Identifier userId);

    boolean existsByDisplayId(String displayId);

}
