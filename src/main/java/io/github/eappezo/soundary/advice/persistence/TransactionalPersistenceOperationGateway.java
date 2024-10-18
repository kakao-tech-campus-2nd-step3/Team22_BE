package io.github.eappezo.soundary.advice.persistence;

import io.github.eappezo.soundary.core.persistence.PersistenceOperation;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalPersistenceOperationGateway implements PersistenceOperationGateway {
    @Override
    @Transactional(readOnly = true)
    public <T> T executeReadOnlyOperation(PersistenceOperation<T> operation) {
        return operation.execute();
    }

    @Override
    @Transactional
    public <T> T executeOperation(PersistenceOperation<T> operation) {
        return operation.execute();
    }
}
