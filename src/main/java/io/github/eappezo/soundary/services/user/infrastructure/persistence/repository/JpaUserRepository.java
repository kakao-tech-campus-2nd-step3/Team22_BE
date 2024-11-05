package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.persistence.infrastructure.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRepository extends JpaRepository<UserEntity, String > {

    @Query("SELECT u.id FROM users u WHERE u.displayId = :displayId")
    Optional<String> findUserIdByDisplayId(String displayId);

    Optional<UserEntity> findByDisplayId(String displayId);

    boolean existsByDisplayId(String displayId);

}
