package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QSharedMusicRetrieveSupport {
    private final JPAQueryFactory jpaQueryFactory;
}
