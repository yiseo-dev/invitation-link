package com.zero.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JoinRequest {
    private Integer userId;
    private Integer invitationId;
    private String link;
    private Boolean isJoin;
}
