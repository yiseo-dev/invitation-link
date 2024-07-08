package com.invitation.repository;

import com.invitation.entity.InvitationInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class InvitationRepository {
    @PersistenceContext
    EntityManager em;

    public InvitationInfo save(InvitationInfo invitationInfo) {
        em.persist(invitationInfo);
        return invitationInfo;
    }

    public InvitationInfo findByUserId(Long invitationId) {
        return em.find(InvitationInfo.class,invitationId);
    }

    public void updateState(InvitationInfo invitationInfo) {
        em.merge(invitationInfo);
    }
}
