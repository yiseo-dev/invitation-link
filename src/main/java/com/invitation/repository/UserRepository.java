package com.invitation.repository;

import com.invitation.entity.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository{

    @PersistenceContext
    EntityManager em;
    public UserInfo findByUserId(Long userId) {
        return em.find(UserInfo.class,userId);
    }
    public void update(UserInfo userInfo){
        em.merge(userInfo);
    }
    public UserInfo saveUserInfo(UserInfo userInfo) {
        em.persist(userInfo);
        return userInfo;
    }
}
