package com.zero.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String useYn;
}
