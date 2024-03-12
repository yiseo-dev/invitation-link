package com.zero.model.invitation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LinkInfoRequest {
    private Integer userId;
    private Integer invitationId;
}
