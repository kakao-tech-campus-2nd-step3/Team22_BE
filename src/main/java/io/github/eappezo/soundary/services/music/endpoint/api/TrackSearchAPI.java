package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.services.music.endpoint.api.dto.SearchTrackResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TrackSearchAPI {

    SearchTrackResponse search(String platform, String query);

}
