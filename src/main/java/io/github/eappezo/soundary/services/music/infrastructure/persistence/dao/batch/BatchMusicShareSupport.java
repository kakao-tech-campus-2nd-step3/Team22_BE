package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.batch;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.identification.IdentifierGenerator;
import io.github.eappezo.soundary.services.music.application.share.MusicShareSupport;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicEntity;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.JpaSharedMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchMusicShareSupport implements MusicShareSupport {
    private final IdentifierGenerator identifierGenerator;
    private final JdbcTemplate jdbcTemplate;
    private final JpaSharedMusicRepository jpaSharedMusicRepository;

    private static final String sql = """
           INSERT INTO shared_music_target (shared_music_id, target_user_id)
           VALUES (?, ?);
           """;

    @Override
    public SharedMusic shareTrack(
            Identifier fromUserId,
            List<Identifier> toUserIds,
            Track track,
            String comment
    ) {
        Identifier sharedMusicId = identifierGenerator.generate();
        SharedMusicEntity sharedMusic = jpaSharedMusicRepository.save(
                SharedMusicEntity.newSharedMusic(
                        sharedMusicId,
                        fromUserId,
                        track.id(),
                        comment
                )
        );
        List<Object[]> batchArgs = new ArrayList<>();
        for (Identifier toUserId : toUserIds) {
            batchArgs.add(new Object[]{sharedMusicId.toString(), toUserId.toString()});
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);

        return SharedMusic.of(
                sharedMusicId,
                fromUserId,
                track,
                comment,
                sharedMusic.getCreatedAt()
        );
    }
}
