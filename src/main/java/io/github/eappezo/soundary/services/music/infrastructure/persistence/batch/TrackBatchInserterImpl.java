package io.github.eappezo.soundary.services.music.infrastructure.persistence.batch;

import io.github.eappezo.soundary.core.identification.IdentifierGenerator;
import io.github.eappezo.soundary.services.music.application.PlatformTrackId;
import io.github.eappezo.soundary.services.music.application.search.SearchedTrackDto;
import io.github.eappezo.soundary.services.music.application.search.TrackBatchInserter;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.TrackEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrackBatchInserterImpl implements TrackBatchInserter {
    private final IdentifierGenerator identifierGenerator;
    private final JdbcTemplate jdbcTemplate;

    private static final String linkBatchSql = """
            INSERT INTO platform_track_link (platform, platform_track_id, track_id)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE track_id = VALUES(track_id)
            """;

    private static final String insertBatchSql = """
            INSERT INTO `tracks` (id, title, album_title, serialized_artists, album_cover_url, preview_mp3_url, duration_in_seconds)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE title = VALUES(title), album_title = VALUES(album_title), serialized_artists = VALUES(serialized_artists),
            album_cover_url = VALUES(album_cover_url), preview_mp3_url = VALUES(preview_mp3_url), duration_in_seconds = VALUES(duration_in_seconds)
            """;

    @Override
    @Transactional
    public void insert(List<SearchedTrackDto> tracks) {
        List<SearchedTrackDto> tracksToInsert = filterExistingTrack(tracks);
        List<Object[]> insertBatchArgs = new ArrayList<>();
        List<Object[]> linkTrackBatchArgs = new ArrayList<>();
        for (SearchedTrackDto track : tracksToInsert) {
            String trackId = identifierGenerator.generate().toString();
            Object[] insertValues = {
                    trackId,
                    track.title(),
                    track.album(),
                    String.join(TrackEntity.ARTISTS_DELIMITER, track.artists()),
                    track.albumCoverUrl(),
                    track.previewMp3Url(),
                    track.durationInSeconds()
            };
            insertBatchArgs.add(insertValues);
            Object[] linkValues = {
                    track.id().platform().name(),
                    track.id().platformTrackId(),
                    trackId
            };
            linkTrackBatchArgs.add(linkValues);
        }
        jdbcTemplate.batchUpdate(insertBatchSql, insertBatchArgs);
        jdbcTemplate.batchUpdate(linkBatchSql, linkTrackBatchArgs);
    }

    private List<SearchedTrackDto> filterExistingTrack(List<SearchedTrackDto> tracks) {
        String sql = """
                SELECT platform, platform_track_id
                FROM platform_track_link WHERE (platform, platform_track_id) IN (%s)
        """;
        String inSql = tracks.stream()
                .map(track -> String.format("('%s', '%s')",
                        track.id().platform(),
                        track.id().platformTrackId())
                )
                .collect(joining(","));

        String finalSql = String.format(sql, inSql);
        List<PlatformTrackId> existingTrackIdentifiers = jdbcTemplate.query(finalSql,
                (rs, rowNum) -> new PlatformTrackId(
                        MusicPlatform.valueOf(rs.getString("platform")),
                        rs.getString("platform_track_id")
                )
        );
        Set<PlatformTrackId> existingSet = new HashSet<>(existingTrackIdentifiers);
        return tracks.stream()
                .filter(track -> !existingSet.contains(track.id()))
                .collect(Collectors.toList());
    }
}
