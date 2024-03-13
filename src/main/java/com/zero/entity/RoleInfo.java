package com.zero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ROLE_INFO")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false, length = 4)
    private String roleName;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

}
