package io.github.eappezo.soundary.advice.async;

import io.github.eappezo.soundary.core.async.AsyncAdvice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultAsyncAdvice implements AsyncAdvice {

    @Async
    @Override
    public void runAsync(Runnable runnable) {
        runnable.run();
    }
}
