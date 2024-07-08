package com.invitation.repository;

import com.invitation.entity.RoleInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {
    @PersistenceContext
    EntityManager em;

    public RoleInfo findRoleById(Long userId) {
        return em.find(RoleInfo.class, userId);
    }
    public void save(RoleInfo roleInfo) { em.persist(roleInfo);
    }
}
