package com.invitation.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JoinRequest {
    private Long userId;
    private Long invitationId;
    private String link;
    private Boolean isJoin;
}
