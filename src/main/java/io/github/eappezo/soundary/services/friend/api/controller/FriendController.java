package io.github.eappezo.soundary.services.friend.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.api.dto.request.FriendRequest;
import io.github.eappezo.soundary.services.friend.api.dto.response.FriendsResponse;
import io.github.eappezo.soundary.services.friend.api.dto.response.ReceivedFriendRequestsResponse;
import io.github.eappezo.soundary.services.friend.api.dto.response.SentFriendRequestsResponse;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.application.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

@RestController
@RequestMapping("/api/v1/friends")
@AllArgsConstructor
public class FriendController implements FriendAPI {
    private final FriendService friendService;

    @Override
    @PostMapping
    public ResponseEntity<Void> sendOrAcceptFriendRequest(
            @AuthenticatedUser Identifier userId,
            @RequestBody FriendRequest friendRequest
    ) {
        friendService.addFriend(userId, friendRequest.targetDisplayId());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/requests/received/{target-user-id}")
    public ResponseEntity<Void> rejectFriendRequest(
            @AuthenticatedUser Identifier userId,
            @PathVariable("target-user-id") Identifier targetUserId
    ) {
        friendService.rejectFriendRequest(FriendshipDTO.of(userId, targetUserId));
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{target-user-id}")
    public ResponseEntity<Void> deleteFriend(
            @AuthenticatedUser Identifier userId,
            @PathVariable("target-user-id") Identifier targetUserId
    ) {
        friendService.deleteFriend(FriendshipDTO.of(userId, targetUserId));
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<FriendsResponse> getFriends(
            @AuthenticatedUser Identifier userId,
            @RequestParam(name = "label", required = false) List<String> rawLabels
    ) {
        List<Label> labels;
        if (rawLabels == null) {
            labels = emptyList();
        } else {
            labels = rawLabels.stream()
                .map((label) -> Label.from(label.toUpperCase()))
                .toList();
        }
        return ResponseEntity.ok(
            FriendsResponse.from(friendService.getFriendList(userId, labels))
        );
    }

    @Override
    @GetMapping("/requests/received")
    public ResponseEntity<ReceivedFriendRequestsResponse> getReceivedRequests(
        @AuthenticatedUser Identifier userId
    ) {
        List<FriendRequestInfo> receivedRequests = friendService.getReceivedRequests(userId);

        return ResponseEntity.ok(ReceivedFriendRequestsResponse.from(receivedRequests));
    }

    @Override
    @GetMapping("/requests/sent")
    public ResponseEntity<SentFriendRequestsResponse> getSentRequests(
        @AuthenticatedUser Identifier userId
    ) {
        List<FriendRequestInfo> sentRequests = friendService.getSentRequests(userId);

        return ResponseEntity.ok(SentFriendRequestsResponse.from(sentRequests));
    }
}
