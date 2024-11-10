package io.github.eappezo.soundary.advice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaByteArrayImageStorage extends JpaRepository<ImageEntity, String> {
}
