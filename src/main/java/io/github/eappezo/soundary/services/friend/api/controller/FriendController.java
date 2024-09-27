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
public class FriendController {

    private final FriendService friendService;

    @PostMapping()
    ResponseEntity<String> sendOrAcceptFriendRequest(FriendRequest friendRequest) {
        friendService.addFriend(FriendshipDTO.from(friendRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body("create");
    }

    @DeleteMapping()
    ResponseEntity<String> rejectFriendRequest(FriendRequest friendRequest) {
        friendService.rejectFriendRequest(FriendshipDTO.from(friendRequest));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete");
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteFriend(FriendRequest friendRequest) {
        friendService.deleteFriend(friendRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("delete");
    }

    @GetMapping()
    ResponseEntity<FriendListResponse> getFriendList(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getFriendList(userId.toString())));
    }

    @GetMapping("/requests/received")
    ResponseEntity<FriendListResponse> getRequestReceivedList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(FriendListResponse.from(friendService.getRequestReceivedList(
            userId.toString())));
    }

    @GetMapping("/requests/sent")
    ResponseEntity<FriendListResponse> getSentRequestFriendList(
        @AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
            FriendListResponse.from(friendService.getSentRequestList(userId.toString())));
    }

}
