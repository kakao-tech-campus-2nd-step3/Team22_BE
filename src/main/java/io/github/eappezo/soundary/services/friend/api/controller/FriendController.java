package io.github.eappezo.soundary.services.friend.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.api.dto.FriendDeleteRequest;
import io.github.eappezo.soundary.services.friend.api.dto.FriendListResponse;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRejectRequest;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRequest;
import io.github.eappezo.soundary.services.friend.api.dto.FriendshipResponse;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.application.service.FriendService;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@AllArgsConstructor
public class FriendController implements FriendAPI {

    private final FriendService friendService;

    @PostMapping()
    public ResponseEntity<Void> sendOrAcceptFriendRequest(@AuthenticatedUser Identifier userId,
        FriendRequest friendRequest) {
        friendService.addFriend(FriendshipDTO.of(userId, friendRequest.toUserId()));
        return ResponseEntity.created(
            URI.create("api/v1/friends/requests/sent" + friendRequest.rawToUserId())).build();
    }

    @DeleteMapping("/requests")
    public ResponseEntity<Void> rejectFriendRequest(@AuthenticatedUser Identifier userId,
        FriendRejectRequest friendRejectRequest) {
        friendService.rejectFriendRequest(FriendshipDTO.of(userId, friendRejectRequest.toUserId()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteFriend(@AuthenticatedUser Identifier userId,
        FriendDeleteRequest friendDeleteRequest) {
        friendService.deleteFriend(FriendshipDTO.of(userId, friendDeleteRequest.toUserId()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<FriendListResponse> getFriendList(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getFriendList(userId)));
    }

    @GetMapping("/requests/received")
    public ResponseEntity<FriendListResponse> getRequestReceivedList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getRequestReceived(userId)));
    }

    @GetMapping("/requests/sent")
    public ResponseEntity<FriendListResponse> getSentRequestFriendList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getSentRequestList(userId)));
    }

    @GetMapping("/requests/sent/{toUserId}")
    public ResponseEntity<FriendshipResponse> getSentRequest(
        @AuthenticatedUser Identifier fromUserId, @PathVariable String toUserId) {
        return ResponseEntity.ok(
            FriendshipResponse.from(friendService.getFriend(
                new FriendshipDTO(fromUserId, Identifier.fromString(toUserId)))));
    }

}
