package com.invitation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "USER_INFO")
@RequiredArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String userName;

    @Column(name = "USER_EMAIL", nullable = false, length = 100)
    private String userEmail;

    @Column(name = "USER_PHONE_NUMBER", nullable = false, length = 50)
    private String userPhoneNumber;

    @Column(name = "USE_YN", nullable = false, length = 2)
    private String useYn;

}
