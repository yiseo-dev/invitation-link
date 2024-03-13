package com.zero.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LinkInfoRequest {
    private Long userId;
    private Long invitationId;
}
