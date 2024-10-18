package io.github.eappezo.soundary.services.friend.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.api.dto.response.ReceivedFriendRequestsResponse;
import io.github.eappezo.soundary.services.friend.api.dto.response.SentFriendRequestsResponse;
import io.github.eappezo.soundary.services.friend.api.dto.response.FriendsResponse;
import io.github.eappezo.soundary.services.friend.api.dto.request.FriendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "친구 관리 API", description = "친구 요청, 친구 목록을 관리합니다.")
public interface FriendAPI {

    @Operation(summary = "친구 요청 전송/수락", description = "친구 요청을 보내거나 수락합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "친구 요청 전송/수락 성공")
    })
    ResponseEntity<Void> sendOrAcceptFriendRequest(@AuthenticatedUser Identifier userId, FriendRequest friendRequest);

    @Operation(summary = "친구 요청 거절", description = "받은 친구 요청을 거절합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "친구 요청 거절 성공")
    })
    ResponseEntity<Void> rejectFriendRequest(
            @AuthenticatedUser Identifier userId,
            @PathVariable("target-user-id") Identifier targetUserId
    );

    @Operation(summary = "친구 삭제", description = "친구 관계를 서로에게서 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "친구 삭제 성공")
    })
    ResponseEntity<Void> deleteFriend(
            @AuthenticatedUser Identifier userId,
            @PathVariable("target-user-id") Identifier targetUserId
    );

    @Operation(summary = "친구 목록 조회", description = "친구 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "친구 목록 조회 성공")
    })
    ResponseEntity<FriendsResponse> getFriends(@AuthenticatedUser Identifier userId);

    @Operation(summary = "받은 친구 요청 목록 조회", description = "받은 친구 요청의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "받은 친구 요청 목록 조회 성공")
    })
    ResponseEntity<ReceivedFriendRequestsResponse> getReceivedRequests(@AuthenticatedUser Identifier userId);

    @Operation(summary = "보낸 친구 요청 목록 조회", description = "보낸 친구 요청의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보낸 친구 요청 목록 조회 성공")
    })
    ResponseEntity<SentFriendRequestsResponse> getSentRequests(
        @AuthenticatedUser Identifier userId
    );
}
