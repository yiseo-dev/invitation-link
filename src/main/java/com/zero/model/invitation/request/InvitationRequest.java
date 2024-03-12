package com.zero.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationRequest {
    private Integer inviterId;
    private Integer inviteeId;
    private String inviteePhoneNumber;
    private String inviteeName;
    private String inviteeEmail;
}
