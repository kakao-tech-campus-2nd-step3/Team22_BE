package io.github.eappezo.soundary.services.friend.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.api.dto.FriendListResponse;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRequest;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.application.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friends")
@AllArgsConstructor
public class FriendController implements FriendAPI{

    private final FriendService friendService;

    @PostMapping()
    public ResponseEntity<String> sendOrAcceptFriendRequest(FriendRequest friendRequest) {
        friendService.addFriend(FriendshipDTO.from(friendRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body("create");
    }

    @DeleteMapping()
    public ResponseEntity<String> rejectFriendRequest(FriendRequest friendRequest) {
        friendService.rejectFriendRequest(FriendshipDTO.from(friendRequest));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFriend(FriendRequest friendRequest) {
        friendService.deleteFriend(friendRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete");
    }

    @GetMapping()
    public ResponseEntity<FriendListResponse> getFriendList(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getFriendList(userId.toString())));
    }

    @GetMapping("/requests/received")
    public ResponseEntity<FriendListResponse> getRequestReceivedList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(FriendListResponse.from(friendService.getRequestReceivedList(
            userId.toString())));
    }

    @GetMapping("/requests/sent")
    public ResponseEntity<FriendListResponse> getSentRequestFriendList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getSentRequestList(userId.toString())));
    }

}
