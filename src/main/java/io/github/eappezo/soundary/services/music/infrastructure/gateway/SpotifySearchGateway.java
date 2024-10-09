package io.github.eappezo.soundary.services.music.infrastructure.gateway;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.application.*;
import io.github.eappezo.soundary.services.music.application.search.SearchedTrackDto;
import io.github.eappezo.soundary.services.music.application.search.TrackSearchGateway;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.MusicPlatformAuthenticationManager;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
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
    public List<SearchedTrackDto> search(String query) {
        PlatformAccessToken token = authenticationManager.getAccessToken(getPlatform());
        String uri = String.format(
                "https://api.spotify.com/v1/search?q=%s&type=track&limit=%d",
                query, limit
        );
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
        public List<SearchedTrackDto> convertToTrackList() {
            return tracks.items
                    .stream()
                    .map(TrackDto::toTrack)
                    .toList();
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
        public SearchedTrackDto toTrack() {
            return new SearchedTrackDto(
                    PlatformTrackId.of(MusicPlatform.SPOTIFY, id),
                    name,
                    artists.stream().map(ArtistDto::name).toList(),
                    album.name(),
                    album.images.getFirst().url(),
                    previewUrl,
                    Duration.ofMillis(durationMs).getSeconds()
            );
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record ExternalIds(
            String isrc
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record ArtistDto(
            String name
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record AlbumDto(
            String name,
            List<TrackCoverDto> images
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record TrackCoverDto(
            Long height,
            Long width,
            String url
    ) {
    }
}
