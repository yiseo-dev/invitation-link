package com.zero.model.invitation;

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
