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
@RequiredArgsConstructor
@Table(name = "INVITATION_INFO")
public class InvitationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVITATION_ID")
    private Long invitationId;

    @Column(name = "INVITER_ID", nullable = false)
    private Long inviterId;

    @Column(name = "INVITEE_ID", nullable = false)
    private Long inviteeId;

    @Column(name = "INVITATION_STATE", nullable = false, length = 4)
    private String invitationState;

    @Column(name = "EXPIRE_DTM", nullable = false, length = 14)
    private String expireDtm;
}
