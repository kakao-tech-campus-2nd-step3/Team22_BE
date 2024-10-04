package io.github.eappezo.soundary.services.music.infrastructure.gateway;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.MusicPlatformAuthenticationManager;
import io.github.eappezo.soundary.services.music.application.PlatformAccessToken;
import io.github.eappezo.soundary.services.music.application.TrackSearchGateway;
import io.github.eappezo.soundary.services.music.domain.Album;
import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.domain.exception.TrackSearchError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;

import static io.github.eappezo.soundary.core.DecodeInputStreamUtil.decodeInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpotifySearchGateway implements TrackSearchGateway {
    @Value("${app.music-search-limit}") private Integer limit;
    private final RestClient restClient;
    private final MusicPlatformAuthenticationManager authenticationManager;

    @Override
    public List<Track> search(String query) {
        PlatformAccessToken token = authenticationManager.getAccessToken(getPlatform());
        String uri = new StringBuilder("https://api.spotify.com/v1/search?q=")
                .append(query)
                .append("&type=track&limit=")
                .append(limit)
                .toString();
        MusicSearchResponse searchResponse = restClient
                .get()
                .uri(uri)
                .headers(
                        headers -> headers.setBearerAuth(token.value())
                )
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (request, response) -> {
                            log.error(
                                    "Failed to get value from Spotify API. body: \n{}",
                                    decodeInputStream(response.getBody())
                            );
                            throw new TrackSearchError(query);
                        }
                )
                .toEntity(MusicSearchResponse.class)
                .getBody();
        assert searchResponse != null;
        return searchResponse.convertToTrackList();
    }

    @Override
    public MusicPlatform getPlatform() {
        return MusicPlatform.SPOTIFY;
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record MusicSearchResponse(
            SearchItems tracks
    ) {
        public List<Track> convertToTrackList() {
            return tracks.items.stream().map(TrackDto::toTrack).toList();
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record SearchItems(
            String href,
            List<TrackDto> items
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record TrackDto(
            String id,
            String name,
            Long durationMs,
            String previewUrl,
            AlbumDto album,
            List<ArtistDto> artists
    ) {
        public Track toTrack() {
            return Track.of(
                    MusicPlatform.SPOTIFY,
                    Identifier.fromString(id),
                    name,
                    artists.stream().map(ArtistDto::toArtist).toList(),
                    album.toAlbum(),
                    previewUrl,
                    Duration.ofMillis(durationMs)
            );
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record ArtistDto(
            String name
    ) {
        public Artist toArtist() {
            return new Artist(name);
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record AlbumDto(
            String name,
            List<TrackCoverDto> images
    ) {
        public Album toAlbum() {
            return new Album(name, images.getFirst().url());
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record TrackCoverDto(
            Long height,
            Long width,
            String url
    ) {
    }
}
