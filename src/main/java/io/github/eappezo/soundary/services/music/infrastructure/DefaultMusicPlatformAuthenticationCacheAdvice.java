package io.github.eappezo.soundary.services.music.infrastructure;

import io.github.eappezo.soundary.services.music.application.MusicPlatformAuthenticationCacheAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class DefaultMusicPlatformAuthenticationCacheAdvice implements MusicPlatformAuthenticationCacheAdvice {
    private static final String DELIMITER = "::";
    private final MusicPlatformAuthenticationCacheAdviceDelegate delegate;

    public <T> T lookAside(Supplier<T> supplier, Object... keys) {
        return delegate.cache(generateKey(keys), supplier);
    }

    @Override
    public <T> T update(Supplier<T> supplier, Object... keys) {
        return delegate.put(generateKey(keys), supplier);
    }

    public <T> T evict(Supplier<T> supplier, Object... keys) {
        return delegate.evict(generateKey(keys), supplier);
    }

    private static String generateKey(Object... keys) {
        return String.join(DELIMITER,
                Arrays.stream(keys)
                        .map(Object::toString)
                        .toArray(String[]::new)
        );
    }

    @Component
    public static class MusicPlatformAuthenticationCacheAdviceDelegate {
        private static final String CACHE_NAME = "MUSIC_PLATFORM_AUTHENTICATION";

        @Cacheable(value = CACHE_NAME, key = "#key")
        public <T> T cache(String key, Supplier<T> supplier) {
            return supplier.get();
        }

        @CachePut(value = CACHE_NAME, key = "#key")
        public <T> T put(String key, Supplier<T> supplier) {
            return supplier.get();
        }

        @CacheEvict(value = CACHE_NAME, key = "#key")
        public <T> T evict(String key, Supplier<T> supplier) {
            return supplier.get();
        }
    }
}
