package com.invitation.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationRequest {
    private Long inviterId;
    private Long inviteeId;
    private String inviteePhoneNumber;
    private String inviteeName;
    private String inviteeEmail;
}
