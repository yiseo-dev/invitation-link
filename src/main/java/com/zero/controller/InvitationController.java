package com.zero.controller;

import com.zero.config.Response;
import com.zero.model.invitation.LinkInfoRequest;
import com.zero.model.invitation.request.InvitationRequest;
import com.zero.model.invitation.request.JoinRequest;
import com.zero.model.invitation.response.InvitationResponse;
import com.zero.model.invitation.response.LinkInfoResponse;
import com.zero.service.invitation.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/link/generate")
    public ResponseEntity<Response> generateInvitation(@RequestBody InvitationRequest request) {
        InvitationResponse response = invitationService.createInvitationLink(request);
        return ResponseEntity.ok(Response.builder()
                .data(response)
                .build());
    }

    @PatchMapping("/{inviteeId}/join")
    public ResponseEntity<HttpStatus> joinInvitation(@PathVariable(value = "inviteeId") Integer inviteeId, @RequestBody JoinRequest request) {
        request.setUserId(inviteeId);
        invitationService.joinInvitation(request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/link")
    public ResponseEntity<Response> findLinkById(LinkInfoRequest request) {
        LinkInfoResponse response = invitationService.findLinkById(request);
        return ResponseEntity.ok(Response.builder().data(response).build());
    }

}
