package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.services.music.application.SimpleTrackDto;
import io.github.eappezo.soundary.services.music.application.TrackBatchUpserter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackBatchUpserterImpl implements TrackBatchUpserter {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void upsert(List<SimpleTrackDto> tracks) {
        String sql = """
                   INSERT INTO `tracks` (platform, platform_track_id, title, album_title, serialized_artists, album_cover_url, preview_mp3_url, duration_in_seconds)
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                   ON DUPLICATE KEY UPDATE title = VALUES(title), album_title = VALUES(album_title), serialized_artists = VALUES(serialized_artists),
                   album_cover_url = VALUES(album_cover_url), preview_mp3_url = VALUES(preview_mp3_url), duration_in_seconds = VALUES(duration_in_seconds)
                """;
        List<Object[]> batchArgs = new ArrayList<>();
        List<TrackEntity> trackEntities = tracks.stream().map(TrackEntity::from).toList();
        for (TrackEntity track : trackEntities) {
            Object[] values = {
                    track.getPlatform().toString(),
                    track.getPlatformTrackId(),
                    track.getTitle(),
                    track.getAlbumTitle(),
                    track.getArtists(),
                    track.getAlbumCoverUrl(),
                    track.getPreviewMp3Url(),
                    track.getDurationInSeconds()
            };
            batchArgs.add(values);
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
