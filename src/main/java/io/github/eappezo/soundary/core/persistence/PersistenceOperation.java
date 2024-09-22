package io.github.eappezo.soundary.core.persistence;

@FunctionalInterface
public interface PersistenceOperation<T> {

    T execute();

}
