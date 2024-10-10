package io.github.eappezo.soundary.core.pushAlarm;

@FunctionalInterface
public interface PushAlarmOperationGateway {
    <T> T excuteOperation(PushAlarmOperation<T> operation);
}
