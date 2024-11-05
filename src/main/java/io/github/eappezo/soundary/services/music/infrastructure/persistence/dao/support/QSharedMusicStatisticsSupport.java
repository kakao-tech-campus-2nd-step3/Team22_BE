package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.services.music.application.share.MostLikedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.MostSharedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicStatisticsSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicEntity.sharedMusicEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicLikeEntity.sharedMusicLikeEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QTrackEntity.trackEntity;

@Component
@RequiredArgsConstructor
public class QSharedMusicStatisticsSupport implements SharedMusicStatisticsSupport {
    private final JPAQueryFactory jpaQueryFactory;
    @Value("${app.shared-music.statistics-limit}")
    private int statisticsLimit;

    @Value("${app.shared-music.exposure-duration-days}")
    private int exposureDurationDays;

    @Override
    public MostSharedTracksDto getMostSharedTracks() {
        return MostSharedTracksDto.from(jpaQueryFactory
                .select(
                        new QTrackProjection(
                                sharedMusicEntity.trackId,
                                trackEntity.title,
                                trackEntity.artists,
                                trackEntity.albumTitle,
                                trackEntity.albumCoverUrl,
                                trackEntity.previewMp3Url,
                                trackEntity.durationInSeconds
                        )
                )
                .from(sharedMusicEntity)
                .join(trackEntity)
                .on(
                        sharedMusicEntity.trackId.eq(trackEntity.id),
                        isWithinExposureDuration()
                )
                .groupBy(sharedMusicEntity.trackId)
                .orderBy(sharedMusicEntity.trackId.count().desc())
                .limit(statisticsLimit)
                .fetch()
                .stream()
                .map(TrackProjection::toDto)
                .toList()
        );
    }

    @Override
    public MostLikedTracksDto getMostLikedTracks() {
        return MostLikedTracksDto.from(jpaQueryFactory
                .select(
                        new QTrackProjection(
                                sharedMusicEntity.trackId,
                                trackEntity.title,
                                trackEntity.artists,
                                trackEntity.albumTitle,
                                trackEntity.albumCoverUrl,
                                trackEntity.previewMp3Url,
                                trackEntity.durationInSeconds
                        )
                )
                .from(sharedMusicLikeEntity)
                .join(sharedMusicEntity)
                .on(sharedMusicLikeEntity.sharedMusicId.eq(sharedMusicEntity.id))
                .join(trackEntity)
                .on(
                        sharedMusicEntity.trackId.eq(trackEntity.id),
                        isWithinExposureDuration()
                )
                .groupBy(sharedMusicEntity.trackId)
                .orderBy(sharedMusicEntity.trackId.count().desc())
                .limit(statisticsLimit)
                .fetch()
                .stream()
                .map(TrackProjection::toDto)
                .toList()
        );
    }

    private BooleanExpression isWithinExposureDuration() {
        return sharedMusicEntity
                .createdAt
                .after(LocalDateTime.now().minusDays(exposureDurationDays));
    }
}
