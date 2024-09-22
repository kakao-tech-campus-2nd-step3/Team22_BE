package io.github.eappezo.soundary.core.persistence;

public interface PersistenceOperationGateway {

    <T> T executeReadOnlyOperation(PersistenceOperation<T> operation);

    <T> T executeOperation(PersistenceOperation<T> operation);

}
