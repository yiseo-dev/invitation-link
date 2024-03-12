package com.zero.entity;

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
    private Integer invitationId;

    @Column(name = "INVITER_ID")
    private Integer inviterId;

    @Column(name = "INVITEE_ID")
    private Integer inviteeId;

    @Column(name = "INVITATION_STATE")
    private String invitationState;

    @Column(name = "EXPIRE_DTM")
    private String expireDtm;
}
