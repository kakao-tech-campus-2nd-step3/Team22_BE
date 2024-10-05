package io.github.eappezo.soundary.core.cache;

import java.util.function.Supplier;

public interface CacheAdvice {

    <T> T lookAside(Supplier<T> supplier, Object... keys);

    <T> T update(Supplier<T> supplier, Object... keys);

    <T> T evict(Supplier<T> supplier, Object... keys);

}
