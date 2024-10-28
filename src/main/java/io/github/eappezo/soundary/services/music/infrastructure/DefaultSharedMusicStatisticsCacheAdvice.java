package io.github.eappezo.soundary.services.music.infrastructure;

import io.github.eappezo.soundary.services.music.application.share.SharedMusicStatisticsCacheAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class DefaultSharedMusicStatisticsCacheAdvice implements SharedMusicStatisticsCacheAdvice {
    private static final String DELIMITER = "::";
    private final SharedMusicStatisticsCacheAdviceDelegate delegate;

    public <T> T lookAside(Supplier<T> supplier, Object... keys) {
        return delegate.cache(generateKey(keys), supplier);
    }

    @Override
    public <T> T update(Supplier<T> supplier, Object... keys) {
        return delegate.put(generateKey(keys), supplier);
    }

    public void evict(Object... keys) {
        delegate.evict(generateKey(keys));
    }

    private static String generateKey(Object... keys) {
        return String.join(DELIMITER,
                Arrays.stream(keys)
                        .map(Object::toString)
                        .toArray(String[]::new)
        );
    }

    @Component
    public static class SharedMusicStatisticsCacheAdviceDelegate {
        private static final String CACHE_NAME = "SHARED_MUSIC_STATISTICS";

        @Cacheable(value = CACHE_NAME, key = "#key")
        public <T> T cache(String key, Supplier<T> supplier) {
            return supplier.get();
        }

        @CachePut(value = CACHE_NAME, key = "#key")
        public <T> T put(String key, Supplier<T> supplier) {
            return supplier.get();
        }

        @CacheEvict(value = CACHE_NAME, key = "#key")
        public void evict(String key) {
        }
    }
}
