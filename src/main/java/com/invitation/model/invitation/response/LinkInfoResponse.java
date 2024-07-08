package com.invitation.model.invitation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LinkInfoResponse {
    private String link;
}
