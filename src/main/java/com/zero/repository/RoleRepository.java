package com.zero.repository;

import com.zero.entity.RoleInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {
    @PersistenceContext
    EntityManager em;

    public RoleInfo findRoleById(Integer userId) {
        return em.find(RoleInfo.class, userId);
    }
    public void save(RoleInfo roleInfo) { em.persist(roleInfo);
    }
}
